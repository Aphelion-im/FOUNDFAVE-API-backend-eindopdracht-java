package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.ContactFormInputDto;
import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.exceptions.ContactFormNotFoundException;
import online.foundfave.foundfaveapi.models.ContactForm;
import online.foundfave.foundfaveapi.repositories.ContactFormRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactFormService {

    private final ContactFormRepository contactFormRepository;

    public ContactFormService(ContactFormRepository contactFormRepository) {
        this.contactFormRepository = contactFormRepository;
    }

    // Basic CRUD methods
    public List<ContactFormOutputDto> getContactFormSubmissions() {
        List<ContactFormOutputDto> contactFormOutputDtoList = new ArrayList<>();
        List<ContactForm> list = contactFormRepository.findAll();
        for (ContactForm contactForm : list) {
            contactFormOutputDtoList.add(transformContactFormToContactFormOutputDto(contactForm));
        }
        return contactFormOutputDtoList;
    }

    public ContactFormOutputDto getContactFormById(Long contactFormId) {
        ContactForm contactForm = contactFormRepository.findById(contactFormId).orElseThrow(() -> new ContactFormNotFoundException("Contact Form with id: " + contactFormId + " not found!"));
        return transformContactFormToContactFormOutputDto(contactForm);
    }

    public ContactFormOutputDto createContactForm(ContactFormInputDto contactFormInputDto) {
        ContactForm contactForm = transformContactFormInputDtoToContactForm(contactFormInputDto);
        ContactForm createdContactForm = contactFormRepository.save(contactForm);
        return transformContactFormToContactFormOutputDto(createdContactForm);
    }

    public void deleteContactFormSubmission(Long contactFormId) {
        if (!contactFormRepository.existsById(contactFormId)) {
            throw new ContactFormNotFoundException("Contact Form with id: " + contactFormId + " not found!");
        }
        contactFormRepository.deleteById(contactFormId);
    }

    public void deleteAllContactForms() {
        contactFormRepository.deleteAll();
    }

    // Repository methods
    public List<ContactFormOutputDto> findContactFormsByNameContains(String name) {
        List<ContactFormOutputDto> contactFormOutputDtoList = new ArrayList<>();
        List<ContactForm> list = contactFormRepository.findByNameContainsIgnoreCase(name);
        for (ContactForm contactForm : list) {
            contactFormOutputDtoList.add(transformContactFormToContactFormOutputDto(contactForm));
        }
        if (contactFormOutputDtoList.isEmpty()) {
            throw new ContactFormNotFoundException("0 results. No contact forms were found!");
        }
        return contactFormOutputDtoList;
    }

    public List<ContactFormOutputDto> findContactFormsByEmailContains(String email) {
        List<ContactFormOutputDto> contactFormOutputDtoList = new ArrayList<>();
        List<ContactForm> list = contactFormRepository.findByEmailContainsIgnoreCase(email);
        for (ContactForm contactForm : list) {
            contactFormOutputDtoList.add(transformContactFormToContactFormOutputDto(contactForm));
        }
        if (contactFormOutputDtoList.isEmpty()) {
            throw new ContactFormNotFoundException("0 results. No contact forms were found!");
        }
        return contactFormOutputDtoList;
    }

    public List<ContactFormOutputDto> findContactFormsByCommentsContains(String comments) {
        List<ContactFormOutputDto> contactFormOutputDtoList = new ArrayList<>();
        List<ContactForm> list = contactFormRepository.findByCommentsContainsIgnoreCase(comments);
        for (ContactForm contactForm : list) {
            contactFormOutputDtoList.add(transformContactFormToContactFormOutputDto(contactForm));
        }
        if (contactFormOutputDtoList.isEmpty()) {
            throw new ContactFormNotFoundException("0 results. No contact forms were found!");
        }
        return contactFormOutputDtoList;
    }

    // Relational methods


    // Image methods


    // Transformers
    // ContactForm to ContactFormOutputDto
    public static ContactFormOutputDto transformContactFormToContactFormOutputDto(ContactForm contactForm) {
        var contactFormOutputDto = new ContactFormOutputDto();
        contactFormOutputDto.contactFormId = contactForm.getContactFormId();
        contactFormOutputDto.name = contactForm.getName();
        contactFormOutputDto.email = contactForm.getEmail();
        contactFormOutputDto.comments = contactForm.getComments();
        contactFormOutputDto.timeStamp = contactForm.getTimeStamp();
        return contactFormOutputDto;
    }

    // ContactFormInputDto to ContactForm
    private ContactForm transformContactFormInputDtoToContactForm(ContactFormInputDto contactFormInputDto) {
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contactFormInputDto.getName());
        contactForm.setEmail(contactFormInputDto.getEmail());
        contactForm.setComments(contactFormInputDto.getComments());
        contactForm.setTimeStamp(LocalDateTime.now());
        return contactForm;
    }
}
