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
// TODO: Get all profiles

    // TODO: find profile by id

    // TODO: Add profile

    // TODO: Update profile

    // TODO: Delete profile


    // Repository methods
// TODO: Search by first name (Contains)
// TODO: Search by last name (Contains)


    // Relational methods


    // Image methods
    // TODO: Upload profilePic


}
