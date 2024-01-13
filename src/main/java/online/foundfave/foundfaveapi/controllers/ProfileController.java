package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.services.ProfileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Basic CRUD methods

    // TODO: Get mapping


    // Repository methods




    // Relational methods


    // Image methods



}
