package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.input.ContactFormInputDto;
import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.services.ContactFormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactforms")
public class ContactFormController {

    private final ContactFormService contactFormService;

    public ContactFormController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    // TODO: Refactor
    @GetMapping("")
    public ResponseEntity<List<ContactFormOutputDto>> getAllContactForms() {
        List<ContactFormOutputDto> contactForms = contactFormService.getContactFormSubmissions();
        return ResponseEntity.ok(contactForms);
    }

    // TODO: Refactor
    @GetMapping("/{id}")
    public ResponseEntity<ContactFormOutputDto> getContactForm(@PathVariable Long id) {
        ContactFormOutputDto contactFormOutputDto = contactFormService.getContactFormSubmission(id);
        return ResponseEntity.ok(contactFormOutputDto);
    }

    // TODO: Refactor
    @PostMapping("/post")
    public ResponseEntity<ContactFormOutputDto> postContactForm(@RequestBody ContactFormInputDto contactFormInputDto) {
        ContactFormOutputDto createdContactForm = contactFormService.postContactFormSubmission(contactFormInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContactForm);
    }

    // TODO: Refactor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContactForm(@PathVariable Long id) {
        contactFormService.deleteContactFormSubmission(id);
        return ResponseEntity.ok().body("Contact Form with id: " + id + " was successfully deleted!");
    }
}
