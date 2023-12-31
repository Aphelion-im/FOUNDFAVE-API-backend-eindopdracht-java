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

    public List<ContactFormOutputDto> getContactFormSubmissions() {
        List<ContactFormOutputDto> collection = new ArrayList<>();
        List<ContactForm> list = contactFormRepository.findAll();
        for (ContactForm contactForm : list) {
            collection.add(transformContactFormToContactFormOutputDto(contactForm));
        }
        return collection;
    }

    public ContactFormOutputDto getContactFormSubmission(Long contactFormId) {
        ContactForm contactForm = contactFormRepository.findById(contactFormId).orElseThrow(() -> new ContactFormNotFoundException("Contact Form with id: " + contactFormId + " not found!"));
        return transformContactFormToContactFormOutputDto(contactForm);
    }

    public ContactFormOutputDto postContactFormSubmission(ContactFormInputDto contactFormInputDto) {
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

    // TODO: if Null check?
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
