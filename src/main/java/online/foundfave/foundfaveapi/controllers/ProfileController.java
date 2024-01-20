package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.ProfileInputDto;
import online.foundfave.foundfaveapi.dtos.output.ProfileOutputDto;
import online.foundfave.foundfaveapi.services.ProfileService;
import online.foundfave.foundfaveapi.utilities.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Basic CRUD methods
    @GetMapping("")
    public ResponseEntity<List<ProfileOutputDto>> getProfiles() {
        List<ProfileOutputDto> profileOutputDtoList = profileService.getProfiles();
        return ResponseEntity.ok().body(profileOutputDtoList);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileOutputDto> getProfileById(@PathVariable("profileId") Long profileId) {
        ProfileOutputDto profileOutputDto = profileService.getProfileById(profileId);
        return ResponseEntity.ok().body(profileOutputDto);
    }

    @PostMapping("")
    public ResponseEntity<Object> createProfile(@Valid @RequestBody ProfileInputDto profileInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        Long newProfileId = profileService.createProfile(profileInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newProfileId).toUriString());
        return ResponseEntity.created(uri).body("New profile created with id: " + newProfileId + ".");
    }

    @PutMapping("/profile/{profileId}")
    public ResponseEntity<Object> updateProfile(@Valid @PathVariable("profileId") Long profileId, @Valid @RequestBody ProfileInputDto profileInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        profileService.updateProfile(profileId, profileInputDto);
        return ResponseEntity.ok().body("Profile with id: " + profileId + " updated!");
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Object> deleteProfile(@PathVariable("profileId") Long profileId) {
        profileService.deleteProfile(profileId);
        return ResponseEntity.status(HttpStatus.OK).body("Profile with id: " + profileId + " successfully deleted!");
    }

    // Repository methods
    @GetMapping("/search/firstname")
    public ResponseEntity<List<ProfileOutputDto>> findProfileByFirstNameContains(@RequestParam("firstname") String firstname) {
        List<ProfileOutputDto> profileOutputDtoList = profileService.findProfileByFirstNameContains(firstname);
        return ResponseEntity.ok(profileOutputDtoList);
    }

    @GetMapping("/search/lastname")
    public ResponseEntity<List<ProfileOutputDto>> findProfileByLastNameContains(@RequestParam("lastname") String lastname) {
        List<ProfileOutputDto> profileOutputDtoList = profileService.findProfileByLastNameContains(lastname);
        return ResponseEntity.ok(profileOutputDtoList);
    }

    // Relational methods
    @GetMapping("profile/{username}")
    public ResponseEntity<ProfileOutputDto> getProfileByUsername(@PathVariable("username") String username) {
        ProfileOutputDto profileOutputDto = profileService.getProfileByUsername(username);
        return ResponseEntity.ok().body(profileOutputDto);
    }

    // Image methods
    // TODO: Upload profilePic
}
