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

    }


    /**
     * Método para desactivar un grupo.
     *
     * @param grupo ID del grupo a desactivar.
     * @throws PersistenceException Si ocurre un error en la persistencia.
     */
    @Override
    public void desactivar(int grupo) throws PersistenceException {

    }

    /**
     * Método para consultar todos los grupos.
     *
     * @return Lista de grupos consultados.
     * @throws PersistenceException Si ocurre un error en la persistencia.
     */
    @Override
    public List<Grupo> consultarGrupos() throws PersistenceException {
        return null;
    }

}
