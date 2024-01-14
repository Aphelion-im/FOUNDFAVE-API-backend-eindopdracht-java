package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {

    List<ContactForm> findByNameContainsIgnoreCase(String name);
    List<ContactForm> findByEmailContainsIgnoreCase(String email);
    List<ContactForm> findByCommentsContainsIgnoreCase(String comments);
}
