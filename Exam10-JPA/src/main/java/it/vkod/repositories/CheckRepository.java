package it.vkod.repositories;

import it.vkod.models.entities.Check;
import it.vkod.models.entities.CheckType;
import it.vkod.models.entities.User;
import it.vkod.utils.JPAFactory;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.sql.Date;
import java.util.*;

public class CheckRepository implements ICheckRepository {


    @Override
    public boolean save(Check check) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();
        // CREATE
        manager.persist(check);
        manager.getTransaction().commit();
        Boolean isSaved = check.getId() > 0L;

        manager.close();

        return isSaved;
    }

    @Override
    public List<Check> findAllByActiveAndCheckedOn(Boolean active, Date checkedOn) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        List<Check> foundChecks = Collections.unmodifiableList(manager
                .createNativeQuery("SELECT * FROM checks WHERE active LIKE :qpActive AND checked_on LIKE :qpCheckedOn", Check.class)
                .setParameter("qpActive", active)
                .setParameter("qpCheckedOn", checkedOn, TemporalType.DATE)
                .getResultList());

        manager.getTransaction().commit();
        manager.close();

        return foundChecks;
    }

    @Override
    public Optional<Check> findByActiveAndCheckedOnAndAttendeeUsername(Boolean active, Date checkedOn, String username) {
        return Optional.empty();
    }

    @Override
    public List<Check> findAllByActiveAndCheckedOnAndTypeIsIn(Boolean active, Date checkedOn, Collection<CheckType> type) {
        return null;
    }

    @Override
    public List<Check> findByActiveAndCheckedOnAndTypeIsInAndAttendeeUsername(Boolean active, Date checkedOn, Set<CheckType> type, String username) {
        return null;
    }

    @Override
    public List<Check> findAllByActiveAndCheckedOnAndOrganizer_Username(Boolean active, Date checkedOn, String username) {
        return null;
    }

    @Override
    public List<Check> findAllByActiveAndCourse(Boolean active, String course) {
        return null;
    }
}
