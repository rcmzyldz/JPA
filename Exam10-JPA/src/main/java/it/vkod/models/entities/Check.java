package it.vkod.models.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.sql.Time;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "checks")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "active")
    Boolean active;

    @Column(name = "checked_in_at")
    Time checkedInAt;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "checked_on")
    Date checkedOn;

    @Column(name = "checked_out_at")
    Time checkedOutAt;

    @Column(name = "course")
    String course;

    @Column(name = "lat")
    Float lat;

    @Column(name = "lon")
    Float lon;

    @Column(name = "pin")
    Integer pin;

    @Column(name = "session")
    String session;

    @Enumerated(EnumType.STRING)
    CheckType type;

    @Column(name = "valid_location")
    Boolean validLocation;


}