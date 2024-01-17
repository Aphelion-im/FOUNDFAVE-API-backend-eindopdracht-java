package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    List<User> findByEnabled(boolean active);
    List<User> findByEmailContainsIgnoreCase(String email);
    List<User> findByUsernameContainsIgnoreCase(String email);
    List<User> findAllByUsername(String username);
}
