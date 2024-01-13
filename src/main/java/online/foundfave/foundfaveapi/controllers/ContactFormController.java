package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.ContactFormInputDto;
import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.services.ContactFormService;
import online.foundfave.foundfaveapi.utils.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactforms")
public class ContactFormController {

    private final ContactFormService contactFormService;

    public ContactFormController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    // Basic CRUD methods

    // TODO: Refactor
    @GetMapping("")
    public ResponseEntity<List<ContactFormOutputDto>> getAllContactForms() {
        List<ContactFormOutputDto> contactForms = contactFormService.getContactFormSubmissions();
        return ResponseEntity.ok(contactForms);
    }

    // TODO: Refactor
    @GetMapping("/{contactFormId}")
    public ResponseEntity<ContactFormOutputDto> getContactForm(@PathVariable Long contactFormId) {
        ContactFormOutputDto contactFormOutputDto = contactFormService.getContactFormSubmission(contactFormId);
        return ResponseEntity.ok(contactFormOutputDto);
    }

    // TODO: Refactor
    @PostMapping("/post")
    public ResponseEntity<Object> postContactForm(@Valid @RequestBody ContactFormInputDto contactFormInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        ContactFormOutputDto createdContactForm = contactFormService.postContactFormSubmission(contactFormInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContactForm);
    }

    // TODO: Refactor
    @DeleteMapping("/{contactFormId}")
    public ResponseEntity<String> deleteContactForm(@PathVariable Long contactFormId) {
        contactFormService.deleteContactFormSubmission(contactFormId);
        return ResponseEntity.ok().body("Contact Form with id: " + contactFormId + " was successfully deleted!");
    }

    // TODO: OneToMany relatie method toevoegen



    // TODO: Add form to user

    // Repository methods


    // Relational methods




}
