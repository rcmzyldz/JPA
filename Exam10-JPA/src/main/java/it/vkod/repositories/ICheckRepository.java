package it.vkod.repositories;
import it.vkod.models.entities.Check;
import it.vkod.models.entities.CheckType;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.Collection;

import java.util.List;
import java.util.Optional;
import java.util.Set;
public interface ICheckRepository {


    boolean save(Check check);

    List< Check > findAllByActiveAndCheckedOn( final Boolean active, final Date checkedOn );

    Optional< Check > findByActiveAndCheckedOnAndAttendeeUsername( final Boolean active, final Date checkedOn, final @NotEmpty String username );

    List< Check > findAllByActiveAndCheckedOnAndTypeIsIn( final Boolean active, final Date checkedOn, final Collection< CheckType > type );

    List< Check > findByActiveAndCheckedOnAndTypeIsInAndAttendeeUsername( final Boolean active, final Date checkedOn, final Set< CheckType > type, final @NotEmpty String username );

    List< Check > findAllByActiveAndCheckedOnAndOrganizer_Username( final Boolean active, final Date checkedOn, final @NotEmpty String username );

    List< Check > findAllByActiveAndCourse( final Boolean active, final String course );
}