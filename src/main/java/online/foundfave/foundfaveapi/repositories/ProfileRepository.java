package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByFirstNameAndLastName (String firstName, String lastName);
    List<Profile> findByFirstNameContainsIgnoreCase(String firstName);
    List<Profile> findByLastNameContainsIgnoreCase(String lastName);
}
