package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.services.ContactFormService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contactform")
public class ContactFormController {

    private final ContactFormService contactFormService;

    public ContactFormController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }










}
