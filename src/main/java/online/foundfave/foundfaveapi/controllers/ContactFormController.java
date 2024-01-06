package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.services.ContactFormService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contactforms")
public class ContactFormController {

    private final ContactFormService contactFormService;

    public ContactFormController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    @GetMapping("")
    public ResponseEntity<List<ContactFormOutputDto>> getAllContactForms() {
        List<ContactFormOutputDto> contactForms = contactFormService.getContactFormSubmissions();
        return ResponseEntity.ok(contactForms);
    }


}
