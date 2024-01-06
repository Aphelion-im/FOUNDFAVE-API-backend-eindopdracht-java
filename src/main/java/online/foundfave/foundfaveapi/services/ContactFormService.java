package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.models.ContactForm;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.ContactFormRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            collection.add(TransformContactFormToContactFormOutputDto(contactForm));
        }
        return collection;
    }











    // Transformers
    // ContactForm to ContactFormOutputDto
    public static ContactFormOutputDto TransformContactFormToContactFormOutputDto(ContactForm contactForm) {
        var contactFormOutputDto = new ContactFormOutputDto();
        contactFormOutputDto.id = contactForm.getId();
        contactFormOutputDto.name = contactForm.getName();
        contactFormOutputDto.email = contactForm.getEmail();
        contactFormOutputDto.comments = contactForm.getComments();
        contactFormOutputDto.submissionDate = contactForm.getSubmissionDate();
        contactFormOutputDto.submissionTime = contactForm.getSubmissionTime();
        return contactFormOutputDto;
    }


}
