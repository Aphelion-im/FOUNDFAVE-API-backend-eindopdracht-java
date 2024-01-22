package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.ContactFormInputDto;
import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.services.ContactFormService;
import online.foundfave.foundfaveapi.utilities.FieldErrorHandling;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contactforms")
public class ContactFormController {

    private final ContactFormService contactFormService;

    public ContactFormController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    // Basic CRUD methods
    @GetMapping("")
    public ResponseEntity<List<ContactFormOutputDto>> getAllContactForms() {
        List<ContactFormOutputDto> contactFormOutputDtoList = contactFormService.getContactFormSubmissions();
        return ResponseEntity.ok(contactFormOutputDtoList);
    }

    @GetMapping("/{contactFormId}")
    public ResponseEntity<ContactFormOutputDto> getContactFormById(@PathVariable Long contactFormId) {
        ContactFormOutputDto contactFormOutputDto = contactFormService.getContactFormById(contactFormId);
        return ResponseEntity.ok(contactFormOutputDto);
    }

    @PostMapping("/post")
    public ResponseEntity<Object> createContactForm(@Valid @RequestBody ContactFormInputDto contactFormInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        ContactFormOutputDto createdContactForm = contactFormService.createContactForm(contactFormInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + createdContactForm.contactFormId).toUriString());
        return ResponseEntity.created(uri).body(createdContactForm);
    }

    @DeleteMapping("/{contactFormId}")
    public ResponseEntity<String> deleteContactForm(@PathVariable Long contactFormId) {
        contactFormService.deleteContactFormSubmission(contactFormId);
        return ResponseEntity.ok().body("Contact Form with id: " + contactFormId + " was successfully deleted!");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAllContactForms() {
        contactFormService.deleteAllContactForms();
        return ResponseEntity.ok().body("All contact forms successfully deleted!");
    }

    // Repository methods
    @GetMapping("/search/name")
    public ResponseEntity<List<ContactFormOutputDto>> findContactFormsByNameContains(@RequestParam("name") String name) {
        List<ContactFormOutputDto> contactFormOutputDtoList = contactFormService.findContactFormsByNameContains(name);
        return ResponseEntity.ok(contactFormOutputDtoList);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<ContactFormOutputDto>> findContactFormsByEmailContains(@RequestParam("email") String email) {
        List<ContactFormOutputDto> contactFormOutputDtoList = contactFormService.findContactFormsByEmailContains(email);
        return ResponseEntity.ok(contactFormOutputDtoList);
    }

    @GetMapping("/search/comments")
    public ResponseEntity<List<ContactFormOutputDto>> findContactFormsByCommentsContains(@RequestParam("comments") String comments) {
        List<ContactFormOutputDto> contactFormOutputDtoList = contactFormService.findContactFormsByCommentsContains(comments);
        return ResponseEntity.ok(contactFormOutputDtoList);
    }

    // Relational methods


    // Image methods
}
