package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
