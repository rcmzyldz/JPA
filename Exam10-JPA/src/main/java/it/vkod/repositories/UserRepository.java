package it.vkod.repositories;

import it.vkod.models.entities.User;
import it.vkod.utils.JPAFactory;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepository implements IUserRepository {
    @Override
    public boolean save(User user) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        // CREATE
        manager.persist(user);
        manager.getTransaction().commit();
        Boolean isSaved = user.getId() > 0L;

        manager.close();

        return isSaved;
    }

    @Override
    public List<User> findAllByUsernameLikeOrPhoneContaining(String username, String phone) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        List<User> foundUsers = Collections.unmodifiableList(manager
                .createNativeQuery("SELECT * FROM users WHERE username LIKE :qpUsername OR phone LIKE :qpPhone", User.class)
                .setParameter("qpUsername", username)
                .setParameter("qpPhone", phone)
                .getResultList());

        manager.getTransaction().commit();
        manager.close();

        return foundUsers;
    }

    @Override
    public List<User> findAllByCourse(String course) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        List<User> foundUsers = Collections.unmodifiableList(manager
                .createNativeQuery("SELECT * FROM users WHERE course LIKE :qpCourse ", User.class)
                .setParameter("qpCourse", course)
                .getResultList());

        manager.getTransaction().commit();
        manager.close();

        return foundUsers;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        User foundUser = (User) manager
                .createNativeQuery("SELECT * FROM users WHERE username LIKE :qpUsername AND password LIKE :qpPassword", User.class)
                .setParameter("qpUsername", username)
                .setParameter("qpPassword", password)
                .getSingleResult();

        manager.getTransaction().commit();
        manager.close();

        return Optional.ofNullable(foundUser);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        User foundUser = (User) manager
                .createNativeQuery("SELECT * FROM users WHERE email LIKE :qpEmail AND password LIKE :qpPassword", User.class)
                .setParameter("qpEmail", email)
                .setParameter("qpPassword", password)
                .getSingleResult();

        manager.getTransaction().commit();
        manager.close();

        return Optional.ofNullable(foundUser);
    }

    @Override
    public Optional<User> findByPhoneAndPassword(String phone, String password) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        User foundUser = (User) manager
                .createNativeQuery("SELECT * FROM users WHERE phone LIKE :qpPhone AND password LIKE :qpPassword", User.class)
                .setParameter("qpPhone", phone)
                .setParameter("qpPassword", password)
                .getSingleResult();

        manager.getTransaction().commit();
        manager.close();

        return Optional.ofNullable(foundUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        User foundUser = (User) manager
                .createNativeQuery("SELECT * FROM users WHERE username LIKE :qpUsername", User.class)
                .setParameter("qpUsername", username)
                .getSingleResult();

        manager.getTransaction().commit();
        manager.close();

        return Optional.ofNullable(foundUser);
    }

    @Override
    public Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone) {

        EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        manager.getTransaction().begin();

        User foundUser = (User) manager
                .createNativeQuery("SELECT * FROM users WHERE username LIKE :qpUsername OR email LIKE :qpEmail OR phone LIKE :qpPhone", User.class)
                .setParameter("qpUsername", username)
                .setParameter("qpEmail", email)
                .setParameter("qpPhone", phone)
                .getSingleResult();

        manager.getTransaction().commit();
        manager.close();

        return Optional.ofNullable(foundUser);
    }

    @Override
    public User getByUsername(String username) {

        Optional<User> foundUser = findByUsername(username);
        foundUser.isPresent();

//        if (foundUser.isPresent()){
//          return ;
//        } else {
//            return null;
//        }


        return null;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return null;
    }
}
