package es.ubu.lsi.dao.conciertos;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.conciertos.Cliente;

/**
 * Clase DAO para el manejo de entidades Cliente.
 *
 * @param <E> Tipo de entidad Cliente.
 * @param <K> Tipo de clave primaria de la entidad Cliente.
 */
public class DAOCliente<E, K> extends JpaDAO<Cliente, String> {

    /**
     * Constructor de DAOCliente.
     *
     * @param em EntityManager utilizado para la persistencia.
     */
    public DAOCliente(EntityManager em) {
        super(em);
    }

    /**
     * Busca un cliente por su NIF.
     *
     * @param nif NIF del cliente a buscar.
     * @return Cliente correspondiente al NIF, o null si no se encuentra.
     */
    @SuppressWarnings("unchecked")
    public Cliente findByNif(String nif) {
        Query query = getEntityManager().createQuery("select c from Cliente c "
                + "where c.nif = ?1");
        query.setParameter(1, nif);

        List<Cliente> clientes = query.getResultList();

        if (!clientes.isEmpty()) return clientes.get(0);

        return null;
    }

    /**
     * Recupera todos los clientes.
     *
     * @return Lista de todos los clientes.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Cliente> findAll() {
        Query query = getEntityManager().createQuery("select c from Cliente c");

        return query.getResultList();
    }
}
