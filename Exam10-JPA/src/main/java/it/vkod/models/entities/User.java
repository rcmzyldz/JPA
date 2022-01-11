package it.vkod.models.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;


    @Column(name = "course")
    String course;

    @Email
    @Column(name = "email", unique = true)
    String email;

    @NotEmpty
    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "password")
    String password;

    @Column(name = "phone")
    String phone;

    @Column(name = "profile")
    String profile;

    @Column(name = "registered_at")
    Time registeredAt;

    @Column(name = "registered_on")
    Date registeredOn;

    @Column(name = "roles")
    String roles;

    @Column(name = "updated_at")
    Time updatedAt;

    @Column(name = "username")
    String username;

    @OneToMany
    @JoinColumn(name = "attendee_id")
    List<Check> attendees = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "organizer_id")
    List<Check> organizers = new ArrayList<>();


}