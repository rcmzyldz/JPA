package it.vkod.repositories;

import it.vkod.models.entities.User;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    boolean save(final User user);

    List<User> findAllByUsernameLikeOrPhoneContaining(final @NotEmpty String username, final String phone);

    List<User> findAllByCourse(final @NotEmpty String course);

    Optional<User> findByUsernameAndPassword(final String username, final String password);

    Optional<User> findByEmailAndPassword(final String email, final String password);

    Optional<User> findByPhoneAndPassword(final String phone, final String password);

    Optional<User> findByUsername(final String username);

    Optional<User> findByUsernameOrEmailOrPhone(final String username, final String email, final String phone);

    User getByUsername(final String username);

    Boolean existsByUsername(final String username);


}