package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.repositories.ContactFormRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactFormService {

    private final ContactFormRepository userRepository;

    public ContactFormService(ContactFormRepository userRepository) {
        this.userRepository = userRepository;
    }
}
