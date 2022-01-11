package it.vkod.views;

import it.vkod.models.entities.Check;
import it.vkod.models.entities.User;
import it.vkod.repositories.CheckRepository;
import it.vkod.repositories.UserRepository;
import it.vkod.utils.JPAFactory;

import javax.persistence.EntityManager;
import java.sql.Date;

public class MainApp {


    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();

        CheckRepository checkRepository = new CheckRepository();

        User user = new User();
        user.setFirstName("Nikola");
        user.setLastName("Tesla");
        user.setEmail("nikola@tesla.be");
        user.setUsername("niko");
        user.setPassword("2341");


        Check check = new Check();

        check.setActive(true);
        check.setCheckedOn(Date.valueOf("2021-11-12"));


        Check check2 = new Check();
        check.setCheckedOn(Date.valueOf("2020-10-11"));
        check.setCheckedInAt(null);
        check.setPin(1234);


        userRepository.save(user);
        checkRepository.save(check);
        checkRepository.save(check2);


        userRepository.findByUsernameAndPassword("niko", "2341");
        checkRepository.findAllByActiveAndCheckedOn(true, Date.valueOf("2021-01-11"));

    }


}
