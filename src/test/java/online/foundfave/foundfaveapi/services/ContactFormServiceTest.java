package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.ContactFormInputDto;
import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.exceptions.ContactFormNotFoundException;
import online.foundfave.foundfaveapi.models.ContactForm;
import online.foundfave.foundfaveapi.models.User;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContactFormServiceTest {

    @Mock
    ContactFormRepository contactFormRepository;

    @InjectMocks
    ContactFormService contactFormService;

    @Captor
    ArgumentCaptor<ContactForm> contactFormArgumentCaptor;

    ContactForm contactForm1;
    ContactForm contactForm2;
    ContactForm contactForm3;
    ContactFormInputDto contactFormInputDto;
    User user1;
    User user2;
    List<ContactForm> contactFormList;

    @BeforeEach
    void setUp() {
        contactFormList = new ArrayList<>();

        contactForm1 = new ContactForm(1L, "Naam", "andre@novi.nl", "Comments", LocalDateTime.now(), user1);
        contactForm2 = new ContactForm(2L, "Naam 2", "andre2@novi.nl", "Comments 2", LocalDateTime.now(), user2);
        contactForm3 = new ContactForm(3L, "Hulk", "hulk@novi.nl", "Something else", LocalDateTime.now(), user1);
        contactFormInputDto = new ContactFormInputDto("Hulk 2", "hulk2@novi.nl", "Something else 2");

        contactFormList.add(contactForm1);
        contactFormList.add(contactForm2);
        contactFormList.add(contactForm3);
    }

    @Test
    @DisplayName("Should get all contact forms")
    void shouldGetAllContactForms() {
        when(contactFormRepository.findAll()).thenReturn(contactFormList);
        List<ContactFormOutputDto> result = contactFormService.getAllContactForms();
        assertEquals(contactFormList.size(), result.size());
        assertEquals(1L, result.get(0).getContactFormId());
        assertEquals(contactForm2.getContactFormId(), result.get(1).getContactFormId());
        assertEquals(contactForm3.getContactFormId(), result.get(2).getContactFormId());
    }

    @Test
    @DisplayName("Should find contact forms by filling in part of a name")
    void shouldFindContactFormsByNameContains() {
        String name = "Naam";
        when(contactFormRepository.findByNameContainsIgnoreCase(name)).thenReturn(List.of(contactForm1, contactForm2));
        List<ContactFormOutputDto> contactformsResult = contactFormService.findContactFormsByNameContains(name);
        assertEquals(contactForm1.getName(), contactformsResult.get(0).getName());
        assertEquals(contactForm2.getName(), contactformsResult.get(1).getName());
    }

    @Test
    @DisplayName("shouldFindContactFormsByNameContains: Should throw contact form not found exception")
    void shouldFindContactFormsByNameContainsThrowsException() {
        assertThrows(ContactFormNotFoundException.class, () -> contactFormService.findContactFormsByNameContains(null));
    }

    @Test
    @DisplayName("Should find contact forms by filling in part of an email")
    void shouldFindContactFormsByEmailContains() {
        String email = "andre";
        when(contactFormRepository.findByEmailContainsIgnoreCase(email)).thenReturn(List.of(contactForm1, contactForm2));
        List<ContactFormOutputDto> contactformsResult = contactFormService.findContactFormsByEmailContains(email);
        assertEquals(contactForm1.getEmail(), contactformsResult.get(0).getEmail());
        assertEquals(contactForm2.getEmail(), contactformsResult.get(1).getEmail());
    }

    @Test
    @DisplayName("Should find contact forms by filling in part of an email: Should throw contact form not found exception")
    void shouldFindContactFormsByEmailContainsThrowsException() {
        assertThrows(ContactFormNotFoundException.class, () -> contactFormService.findContactFormsByEmailContains(null));
    }

    @Test
    @DisplayName("Should find contact forms by filling in part of a comment")
    void shouldFindContactFormsByCommentsContains() {
        String comments = "Comments";
        when(contactFormRepository.findByCommentsContainsIgnoreCase(comments)).thenReturn(List.of(contactForm1, contactForm2));
        List<ContactFormOutputDto> contactformsResult = contactFormService.findContactFormsByCommentsContains(comments);
        assertEquals(contactForm1.getComments(), contactformsResult.get(0).getComments());
        assertEquals(contactForm2.getComments(), contactformsResult.get(1).getComments());
    }

    @Test
    @DisplayName("Should find contact forms by filling in part of a comment: Should throw contact form not found exception")
    void shouldFindContactFormsByCommentsContainsThrowsException() {
        assertThrows(ContactFormNotFoundException.class, () -> contactFormService.findContactFormsByCommentsContains(null));
    }

    @Test
    @DisplayName("Should find a contact form by filling in an ID")
    void shouldGetContactFormById() {
        Long contactFormId = 2L;
        when(contactFormRepository.findById(contactFormId)).thenReturn(Optional.of(contactForm2));
        ContactFormOutputDto contactFormOutputDto = contactFormService.getContactFormById(contactFormId);
        assertEquals(contactFormId, contactFormOutputDto.contactFormId);
        assertEquals("Naam 2", contactFormOutputDto.name);
        assertEquals("andre2@novi.nl", contactFormOutputDto.email);
        assertEquals("Comments 2", contactFormOutputDto.comments);
    }

    @Test
    @DisplayName("Should create a contact form")
    void shouldCreateContactForm() {
        when(contactFormRepository.save(any(ContactForm.class))).thenReturn(contactForm1);
        contactFormService.createContactForm(contactFormInputDto);
        verify(contactFormRepository, times(1)).save(contactFormArgumentCaptor.capture());
        ContactForm contactFormArgumentCaptorValue = contactFormArgumentCaptor.getValue();
        assertNotEquals(contactForm1.getName(), contactFormArgumentCaptorValue.getName());
        assertNotEquals(contactForm1.getEmail(), contactFormArgumentCaptorValue.getEmail());
        assertNotEquals(contactForm1.getComments(), contactFormArgumentCaptorValue.getComments());
    }

    @Test
    @DisplayName("Should delete all contact forms")
    void shouldDeleteAllContactForms() {
        contactFormService.deleteAllContactForms();
        verify(contactFormRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Should delete a contact form by ID")
    void shouldDeleteContactFormById() {
        Long contactFormId = 1L;
        when(contactFormRepository.existsById(contactFormId)).thenReturn(true);
        contactFormService.deleteContactFormById(contactFormId);
        verify(contactFormRepository, times(1)).deleteById(contactFormId);
        assertFalse(contactFormRepository.findById(contactFormId).isPresent());
    }

    @Test
    @DisplayName("Should throw ContactFormNotFoundException")
    void shouldThrowContactFormNotFoundException() {
        Long contactFormId = 1L;
        when(contactFormRepository.findById(contactFormId)).thenReturn(Optional.empty());
        assertThrows(ContactFormNotFoundException.class, () -> contactFormService.deleteContactFormById(contactFormId));
    }
}