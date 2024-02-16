package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.models.ContactForm;
import online.foundfave.foundfaveapi.repositories.ContactFormRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactFormServiceTest {

    @Mock
    ContactFormRepository contactFormRepository;

    @InjectMocks
    ContactFormService contactFormService;

    @Captor
    ArgumentCaptor<ContactForm> argumentCaptor;

    ContactForm contactForm1;
    ContactForm contactForm2;


    @BeforeEach
    void setUp() {
        contactForm1 = new ContactForm(1L, "Naam", "andre@novi.nl", "Comments", LocalDateTime.now(), null);
        contactForm2 = new ContactForm(2L, "Naam 2", "andre2@novi.nl", "Comments 2", LocalDateTime.now(), null);
    }

    @Test
    @DisplayName("Should delete a contact form by ID")
    void shouldDeleteContactFormById() {
        Long contactFormId = 1L;
        when(contactFormRepository.existsById(contactFormId)).thenReturn(true);
        contactFormService.deleteContactFormById(contactFormId);
        verify(contactFormRepository).deleteById(contactFormId);
    }
}