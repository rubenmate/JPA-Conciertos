package es.ubu.lsi.service.conciertos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.dao.conciertos.DAOCliente;
import es.ubu.lsi.dao.conciertos.DAOCompra;
import es.ubu.lsi.dao.conciertos.DAOConcierto;
import es.ubu.lsi.dao.conciertos.DAOGrupo;

import es.ubu.lsi.model.conciertos.Cliente;
import es.ubu.lsi.model.conciertos.Compra;
import es.ubu.lsi.model.conciertos.Concierto;
import es.ubu.lsi.model.conciertos.Grupo;

import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.PersistenceService;

/**
 * Implementación de la interfaz Service.
 */
public class ServiceImpl extends PersistenceService implements Service {

    private EntityManager em;

    private DAOGrupo<Grupo, Integer> grupoDAO;
    private DAOConcierto<Concierto, Integer> conciertoDAO;
    private DAOCliente<Cliente, String> clienteDAO;
    private DAOCompra<Compra, Integer> compraDAO;

    private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    /**
     * Constructor de la clase ServiceImpl.
     */
    public ServiceImpl() {
        super();
    }

    /**
     * Método para realizar la compra de entradas.
     *
     * @param fecha   Fecha de la compra.
     * @param nif     NIF del cliente.
     * @param grupo   ID del grupo.
     * @param tickets Cantidad de entradas a comprar.
     * @throws PersistenceException Si ocurre un error en la persistencia.
     */
    @Override
    public void comprar(Date fecha, String nif, int grupo, int tickets) throws PersistenceException {
        try {
        	em = this.createSession();
        	beginTransaction(em);
        	
        	clienteDAO = new DAOCliente<Cliente, String>(em);
        	compraDAO = new DAOCompra<Compra, Integer>(em);
        	conciertoDAO = new DAOConcierto<Concierto, Integer>(em);
        	grupoDAO = new DAOGrupo<Grupo, Integer>(em);
        	
        	/* Comprobaciones de existencia de cliente, grupo y concierto */
        	
        	Cliente client = clienteDAO.findById(nif);
        	if (client == null) throw new IncidentException(IncidentError.NOT_EXIST_CLIENT);
        	
        	Grupo group = grupoDAO.findById(grupo);
        	if (group == null) throw new IncidentException(IncidentError.NOT_EXIST_MUSIC_GROUP);
        	
        	List<Concierto> concert = conciertoDAO.findByFechaAndGrupo(fecha, grupo);
        	if (concert.size() == 0) throw new IncidentException(IncidentError.NOT_EXIST_CONCERT);
        	
        	/* Comprobacion de disponibilidad de tickets */
        	int availableTickets = concert.get(0).getTickets();
        	if (availableTickets < tickets) throw new IncidentException(IncidentError.NOT_AVAILABLE_TICKETS);
        	
        	/* Actualizar cambio en tickets */
        	conciertoDAO.updateTickets(availableTickets - tickets, concert.get(0));
        	
        	/* Insertar la compra */
        	Compra purchase = new Compra();
        	int purchaseId = compraDAO.findNextId();
        	
        	compraDAO.insertCompra(purchaseId, client, concert.get(0), tickets, purchase);
        	
        	em.persist(purchase);
        	commitTransaction(em);
        	
        } catch (Exception e) {

            logger.error("Exception");
            
            if (em.getTransaction().isActive()) rollbackTransaction(em); // Si la transacción está activa, hacer rollback

            logger.error(e.getLocalizedMessage());
            
            // Relanzado de excepciones
            if (e instanceof IncidentException) {
                throw (IncidentException) e;
            } else {
                throw e;
            }
            
        } finally {
            // Cerrar EntityManager en finally para garantizar su cierre adecuado
            em.close();
        }
    }


    /**
     * Método para desactivar un grupo.
     *
     * @param grupo ID del grupo a desactivar.
     * @throws PersistenceException Si ocurre un error en la persistencia.
     */
    @Override
    public void desactivar(int grupo) throws PersistenceException {
    	try {
    		em = this.createSession();
        	beginTransaction(em);
        	
        	clienteDAO = new DAOCliente<Cliente, String>(em);
        	compraDAO = new DAOCompra<Compra, Integer>(em);
        	conciertoDAO = new DAOConcierto<Concierto, Integer>(em);
        	grupoDAO = new DAOGrupo<Grupo, Integer>(em);
        	
        	/* Comprobacion de existencia de grupo */
        	
        	Grupo group = grupoDAO.findById(grupo);
        	if (group == null) throw new IncidentException(IncidentError.NOT_EXIST_MUSIC_GROUP);
        	
        	grupoDAO.desactivarGrupo(group);
        	
        	commitTransaction(em);

    	} catch (Exception e) {

            logger.error("Exception");
            
            if (em.getTransaction().isActive()) rollbackTransaction(em); // Si la transacción está activa, hacer rollback

            logger.error(e.getLocalizedMessage());
            
            // Relanzado de excepciones
            if (e instanceof IncidentException) {
                throw (IncidentException) e;
            } else {
                throw e;
            }
            
        } finally {
            // Cerrar EntityManager en finally para garantizar su cierre adecuado
            em.close();
        }
    }

    /**
     * Método para consultar todos los grupos.
     *
     * @return Lista de grupos consultados.
     * @throws PersistenceException Si ocurre un error en la persistencia.
     */
    @Override
    public List<Grupo> consultarGrupos() throws PersistenceException {
        try {
        	em = this.createSession();
        	beginTransaction(em);
        	
        	DAOGrupo<Grupo, Integer> grupoDAO = new DAOGrupo<Grupo, Integer>(em);
        	
        	List<Grupo> groupList = grupoDAO.consultar("gruposConciertosComprasYClientes", "javax.persistence.fetchgraph");
        	
        	commitTransaction(em);
        	
        	return groupList;
        } catch (Exception e) {

            logger.error("Exception");
            
            if (em.getTransaction().isActive()) rollbackTransaction(em); // Si la transacción está activa, hacer rollback
        	
            throw e;
        } finally {
            // Cerrar EntityManager en finally para garantizar su cierre adecuado
            em.close();
        }
    }

}
