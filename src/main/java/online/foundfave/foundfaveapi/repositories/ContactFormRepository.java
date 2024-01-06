package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
}
