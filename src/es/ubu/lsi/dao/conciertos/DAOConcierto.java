package es.ubu.lsi.dao.conciertos;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.conciertos.Concierto;
import es.ubu.lsi.service.conciertos.IncidentException;

/**
 * Clase DAO para el manejo de entidades Concierto.
 *
 * @param <E> Tipo de entidad Concierto.
 * @param <K> Tipo de clave primaria de la entidad Concierto.
 */
public class DAOConcierto<E, K> extends JpaDAO<Concierto, Integer> {

    public DAOConcierto(EntityManager em) {
        super(em);
    }
    
    /**
     * Busca conciertos por grupo.
     *
     * @param grupo ID del grupo.
     * @return Lista de conciertos correspondientes al grupo.
     */
    @SuppressWarnings("unchecked")
    public List<Concierto> findByGrupo(int grupo) {
        return getEntityManager()
        		.createQuery("select c from Concierto c where c.grupo.idgrupo =:idGrupo")
                .setParameter("idGrupo", grupo)
                .getResultList();
    }

    /**
     * Busca conciertos por fecha y grupo.
     *
     * @param fecha    Fecha del concierto.
     * @param idGrupo  ID del grupo.
     * @return Lista de conciertos correspondientes a la fecha y grupo especificados.
     * @throws IncidentException Si ocurre algún incidente durante la búsqueda.
     */
    public List<Concierto> findByFechaAndGrupo(Date fecha, int idGrupo) throws IncidentException {
        List<Concierto> conciertos = getEntityManager()
                .createQuery("select c from Concierto c where c.fecha = ?1 and c.grupo.idgrupo = ?2", Concierto.class)
                .setParameter(1, fecha, TemporalType.TIMESTAMP)
                .setParameter(2, idGrupo)
                .getResultList();
        
        return conciertos;
    }
    
    /**
     * Recupera todos los conciertos.
     *
     * @return Lista de todos los conciertos.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Concierto> findAll() {
        Query query = getEntityManager()
        		.createQuery("select c from Concierto c");
        
        List<Concierto> conciertos = query.getResultList();
        
        return conciertos;
    }

    /**
     * Actualiza el número de tickets de un concierto.
     *
     * @param tickets    Número de tickets a actualizar.
     * @param concierto  Concierto al que se actualizará el número de tickets.
     */
    public void updateTickets(int tickets, Concierto concierto) {
        concierto.setTickets(tickets);
    }

}
