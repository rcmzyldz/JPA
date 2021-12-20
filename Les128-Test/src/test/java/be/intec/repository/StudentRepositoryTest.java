package be.intec.repository;

import be.intec.models.Student;
import be.intec.services.exceptions.QueryException;
import be.intec.utils.JPAFactory;
import be.intec.utils.StudentUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static be.intec.services.exceptions.ExceptionMessages.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {

    private static int emailIndex = 0;


    private String nextUniqueEmail() {
        return ("student" + (++emailIndex) + "@mail.be");
    }

    /*
    private static int emailIndex = 0;

private String nextUniqueEmail() {
    return ("student" + (++emailIndex) + "@mail.be");
}
*/

    /*
    Exception exception = assertThrows(
        QueryException.class,
        () -> {
        });

String expectedMessage = STUDENT_NAME_IS_NOT_VALID.getBody();
String actualMessage = exception.getMessage();

assertTrue(actualMessage.contains(expectedMessage));
*/

    private final StudentRepository repository = new StudentRepository();

    private static int uniqueTestIndex;

    /*private Student findStudentById(Long id){
        final EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        Student foundStudent = manager.find(Student.class, id);
        return foundStudent;

    }
*/
    private Student generateNewStudentRecord(Student student) {

        final EntityManager manager = JPAFactory.getEntityManagerFactory().createEntityManager();

        student.setId(null);
        manager.getTransaction().begin();

        manager.persist(student);
        manager.getTransaction().commit();
        manager.close();

        return student;
    }

    @Order(1)
    @Test
    void should_save_succeed() {


        Student student = new Student();
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail("john.doe" + (uniqueTestIndex++) + "@mail.be");
        student.setName("John Doe");

        Long savedStudentId = repository.save(student);

        assertNotNull(savedStudentId);
        assertNotEquals(0L, savedStudentId);
    }

    @Test
    void should_save_fail_when_student_is_duplicate() {


        final String duplicateEmail = "duplicate_email_" + (uniqueTestIndex++) + "@mail.be";

        Student student = new Student();
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail(duplicateEmail);
        student.setName("John Doe");

        generateNewStudentRecord(student);

        Student duplicate = new Student();
        duplicate.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        duplicate.setEmail(duplicateEmail);
        duplicate.setName("John Doe");

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    // throws an exception due duplicate entry .
                    repository.save(duplicate);
                });

        // OPTIONAL
        String expectedMessage = STUDENT_ALREADY_EXISTS.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_save_fail_when_student_is_null() {

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.save(null);
                });

        String expectedMessage = STUDENT_INFO_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_save_fail_when_student_name_has_less_than_2_chars() {

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    Student student = new Student();
                    student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
                    student.setEmail("email" + (uniqueTestIndex++) + "@mail.be");

                    student.setName("A");
                    repository.save(student);
                });

        String expectedMessage = STUDENT_NAME_IS_NOT_VALID.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    void should_save_fail_when_student_email_is_null() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(new Random().nextInt(200) + 10);

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.save(student);
                });

        String expectedMessage = STUDENT_EMAIL_IS_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    void should_save_fail_when_student_email_is_invalid() {

        Student student = new Student();
        student.setName("John Doe");
        student.setAge(new Random().nextInt(200) + 10);
        student.setEmail("john.doe.be");

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.save(student);
                });

        String expectedMessage = STUDENT_EMAIL_IS_NOT_VALID.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    void should_update_name_fail_when_student_id_is_null() {


        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.updateName(null, "Justin");
                });

        String expectedMessage = STUDENT_ID_IS_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }


    @Test
    void should_update_name_fail_when_student_id_is_smaller_than_null() {


        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.updateName(-1L, "Justin");
                });

        String expectedMessage = STUDENT_ID_IS_NOT_VALID.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    void should_update_name_fail_when_student_name_is_null() {


        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.updateName(1L, null);
                });

        String expectedMessage = STUDENT_NAME_IS_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    void should_update_name_fail_when_student_name_has_less_than_2_chars() {

        Exception exception = assertThrows(
                QueryException.class,
                () -> {

                    repository.updateName(1L, "A");
                });

        String expectedMessage = STUDENT_NAME_IS_NOT_VALID.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_update_name_succeed() {


        Student student = new Student();
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail("john.doe" + (uniqueTestIndex++) + "@mail.be");

        Student savedStudent = generateNewStudentRecord(student);
        Long updatedStudentId = repository.updateName(savedStudent.getId(), "Nikola");

        assertNotNull(updatedStudentId);
    }

    @Test
    void should_update_email_fail_when_id_is_null() {

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.updateEmail(null, "Justin");
                });

        String expectedMessage = STUDENT_ID_IS_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_update_email_succeed() {

        Student student = new Student();
        student.setName("Kim Jong");
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail("kim.jong" + (uniqueTestIndex++) + "@mail.be");

        Student savedStudent = generateNewStudentRecord(student);
        Long updatedStudentId = repository.updateEmail(savedStudent.getId(), "myNewEmail@mail.be");

        assertTrue(updatedStudentId > 0);
        assertNotNull(updatedStudentId);

    }

    @Test
    void should_update_email_fail_when_student_name_is_null() {


        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.updateEmail(1L, null);
                });

        String expectedMessage = STUDENT_EMAIL_IS_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    void should_update_email_fail_when_student_id_is_smaller_than_null() {


        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.updateEmail(-1L, "Justin");
                });

        String expectedMessage = STUDENT_ID_IS_NOT_VALID.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }


    @Test
    void should_update_email_fail_when_student_with_this_id_is_not_found() {

        Student student = new Student();
        student.setName("John Doe");
        student.setAge(new Random().nextInt(100) + 10);
        student.setEmail(nextUniqueEmail());

        StudentUtils.saveStudentToDatabase(student);

        Exception exception = assertThrows(
                QueryException.class,
                () -> {

                    Optional<Long> oId = StudentUtils.getLastStudentId();

                    if (oId.isPresent()) {
                        repository.updateEmail(oId.get() + 1, "dontcarenow@gmail.com");
                    }

                });

        String expectedMessage = STUDENT_NOT_FOUND.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_delete_email_fail_when_id_is_null() {

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.delete(null);
                });

        String expectedMessage = STUDENT_ID_IS_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_delete_email_fail_when_id_is_negative_or_zero() {

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.delete(-500L);
                });

        String expectedMessage = STUDENT_ID_IS_NOT_VALID.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_delete_email_fail_when_student_with_this_id_is_not_found() {

        Student student = new Student();
        student.setName("John Doe");
        student.setAge(new Random().nextInt(100) + 10);
        student.setEmail(nextUniqueEmail());

        StudentUtils.saveStudentToDatabase(student);

        Exception exception = assertThrows(
                QueryException.class,
                () -> {

                    Optional<Long> oId = StudentUtils.getLastStudentId();

                    if (oId.isPresent()) {
                        repository.delete(oId.get() + 1);
                    }

                });

        String expectedMessage = STUDENT_NOT_FOUND.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_update_name_fail_when_student_with_this_id_is_not_found() {

        Student student = new Student();
        student.setName("John Doe");
        student.setAge(new Random().nextInt(100) + 10);
        student.setEmail(nextUniqueEmail());

        StudentUtils.saveStudentToDatabase(student);

        Exception exception = assertThrows(
                QueryException.class,
                () -> {

                    Optional<Long> oId = StudentUtils.getLastStudentId();

                    if (oId.isPresent()) {
                        repository.updateName(oId.get() + 1, "Jonny");
                    }

                });

        String expectedMessage = STUDENT_NOT_FOUND.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

  /*  @Test
    void should_update_name_fail_when_student_with_this_id_is_null() {

        Student student = new Student();
        student.setName("John Doe");
        student.setAge(new Random().nextInt(100) + 10);
        student.setEmail(nextUniqueEmail());

        StudentUtils.saveStudentToDatabase(student);

        Exception exception = assertThrows(
                QueryException.class,
                () -> {

                    Long oId = StudentUtils.getLastStudentId();


                        repository.updateName(0L, "Jonny");


                });

        String expectedMessage = UNDEFINED_EXCEPTION.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
*/


    @Test
    void should_find_by_id_succeed() {


        Student student = new Student();
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail("john.doe" + (uniqueTestIndex++) + "@mail.be");

        Student savedStudent = generateNewStudentRecord(student);
        Optional<Student> oId = repository.findById(savedStudent.getId());


        assertNotNull(oId);
        assertTrue(savedStudent != null);

    }

    @Test
    void should_find_by_id_fail_when_id_is_null() {

        Exception exception = assertThrows(
                QueryException.class,
                () -> {
                    repository.findById(null);
                });

        String expectedMessage = STUDENT_ID_IS_REQUIRED.getBody();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void should_exists_by_id_succeed() {


        Student student = new Student();
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail("john.doe" + (uniqueTestIndex++) + "@mail.be");

        Student savedStudent = generateNewStudentRecord(student);
        Boolean existsById = repository.existsById(savedStudent.getId());


        assertNotNull(existsById);
        assertTrue(existsById);

    }

    @Test
    void should_find_all_succeed() {


        Student student = new Student();
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail("john.doe" + (uniqueTestIndex++) + "@mail.be");

        Student savedStudent = generateNewStudentRecord(student);

        Student student2 = new Student();
        student.setAge((uniqueTestIndex++) + new Random().nextInt(100) + 10);
        student.setEmail("john.dash" + (uniqueTestIndex++) + "@mail.be");
        Student savedStudent2 = generateNewStudentRecord(student2);
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(student);
        studentList.add(student2);

        List<Student> foundStudentList = repository.findAll(1,2);


        assertNotNull(foundStudentList);
        assertTrue(foundStudentList != null);

    }


}