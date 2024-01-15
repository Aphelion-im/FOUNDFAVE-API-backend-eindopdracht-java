package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findByFirstNameContainsIgnoreCase(String firstName);
    List<Profile> findByLastNameContainsIgnoreCase(String lastName);
}
