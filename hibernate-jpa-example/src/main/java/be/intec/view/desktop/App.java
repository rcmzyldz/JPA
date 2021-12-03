package be.intec.view.desktop;

import javax.persistence.EntityManager;

import be.intec.models.entities.Student;
import be.intec.utils.JPAFactory;


public class App {

    public static void main(String[] args) {

        EntityManager entityManager =
                JPAFactory.getEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();

        Student student = new Student("Justin", "Bieber", "justin@bieber.com");
        entityManager.persist(student);

        entityManager.getTransaction().commit();

        entityManager.close();
        JPAFactory.shutdown();
    }
}