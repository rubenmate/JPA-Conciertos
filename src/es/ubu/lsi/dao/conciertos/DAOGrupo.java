package es.ubu.lsi.dao.conciertos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.conciertos.Grupo;

/**
 * Clase DAO para el manejo de entidades Grupo.
 *
 * @param <E> Tipo de entidad Grupo.
 * @param <K> Tipo de clave primaria de la entidad Grupo.
 */
public class DAOGrupo<E, K> extends JpaDAO<Grupo, Integer> {

    public DAOGrupo(EntityManager em) {
        super(em);
    }

    /**
     * Desactiva un grupo.
     *
     * @param grupo Grupo a desactivar.
     */
    public void desactivarGrupo(Grupo grupo) {
        if (grupo.getActivo() == 1) grupo.setActivo(0);
    }

    /**
     * Consulta grupos utilizando un nombre de grafo y una pista.
     *
     * @param nombreGrafo Nombre del grafo.
     * @param pista       Pista para la consulta.
     * @return Lista de grupos consultados.
     */
    public List<Grupo> consultar(String nombreGrafo, String pista) {
        return getEntityManager()
        		.createNamedQuery("Grupo.findAll", Grupo.class)
                .setHint(pista, getEntityManager()
                .getEntityGraph(nombreGrafo))
                .getResultList();
    }

    /**
     * Recupera todos los grupos.
     *
     * @return Lista de todos los grupos.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Grupo> findAll() {
        Query query = getEntityManager()
        		.createQuery("select g from grupo g");
        
        List<Grupo> grupos = query.getResultList();
        
        return grupos;
    }
}
