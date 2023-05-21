package es.ubu.lsi.dao.conciertos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.conciertos.*;
import es.ubu.lsi.service.conciertos.IncidentException;

/**
 * Clase DAO para el manejo de entidades Compra.
 *
 * @param <E> Tipo de entidad Compra.
 * @param <K> Tipo de clave primaria de la entidad Compra.
 */
public class DAOCompra<E, K> extends JpaDAO<Compra, Integer> {

    public DAOCompra(EntityManager em) {
        super(em);
    }
    
    /**
     * Encuentra el siguiente ID de compra disponible.
     *
     * @return Siguiente ID de compra disponible.
     */
    public int findNextId() {
    	Compra compra = getEntityManager()
    	        .createQuery("select c from Compra c where c.idcompra = (select max(c2.idcompra) from Compra c2)", Compra.class)
    	        .getSingleResult();
        
        int currentId = compra.getIdcompra();

        return currentId + 1;
    }

    /**
     * Busca compras por cliente.
     *
     * @param idCliente ID del cliente.
     * @return Lista de compras correspondientes al cliente.
     * @throws IncidentException Si ocurre un incidente durante la búsqueda.
     */
    @SuppressWarnings("unchecked")
    public List<Compra> findByCliente(int idCliente) throws IncidentException {
        Query query = getEntityManager()
        		.createQuery("select c from Compra c where c.idcliente = :idCliente");
        
        query.setParameter("idCliente", idCliente);
        
        List<Compra> compras = query.getResultList();
        
        if (!compras.isEmpty())  return compras;

        return null;
    }
    
    /**
     * Busca todas las compras realizadas por un grupo.
     *
     * @param grupo ID del grupo.
     * @return Lista de todas las compras realizadas por el grupo.
     */
    public List<Compra> findAllByGroup(int grupo) {
    	List<Compra> compras = getEntityManager()
    	        .createQuery("select c from Compra c where c.concierto.grupo.idgrupo = :grupo", Compra.class)
    	        .setParameter("grupo", grupo)
    	        .getResultList();
        
        return compras;
    }

    /**
     * Recupera todas las compras.
     *
     * @return Lista de todas las compras.
     */
    @Override
    public List<Compra> findAll() {
        List<Compra> compras = getEntityManager()
        		.createQuery("select c from Compra c", Compra.class)
                .getResultList();

        return compras;
    }

    /**
     * Busca compras por concierto.
     *
     * @param concierto Concierto para el cual buscar las compras.
     * @return Lista de compras correspondientes al concierto.
     */
    @SuppressWarnings("unchecked")
    public List<Compra> findByConcierto(Concierto concierto) {
        Query query = getEntityManager()
                .createQuery("select c from Compra c where idConcierto =:idConcierto");
        
        query.setParameter("idConcierto", concierto);
        
        List<Compra> compras = query.getResultList();
        
        if (!compras.isEmpty()) return compras;
        
        return null;
    }

    /**
     * Inserta una compra.
     *
     * @param idCompra   ID de la compra.
     * @param cliente    Cliente de la compra.
     * @param concierto  Concierto de la compra.
     * @param tickets    Número de tickets de la compra.
     * @param compra     Compra a insertar.
     */
    public void insertCompra(int idCompra, Cliente cliente, Concierto concierto, int tickets, Compra compra) {
        compra.setIdcompra(idCompra);
        compra.setCliente(cliente);
        compra.setConcierto(concierto);
        compra.setNTickets(tickets);
    }

    /**
     * Elimina compras por concierto.
     *
     * @param concierto Concierto para el cual eliminar las compras.
     */
    public void removeByConcierto(Concierto concierto) {        
        getEntityManager()
        	.createQuery("delete from Compra c where c.concierto.idconcierto = ?1")
            .setParameter(1, concierto.getIdconcierto()).executeUpdate();
    }

}
