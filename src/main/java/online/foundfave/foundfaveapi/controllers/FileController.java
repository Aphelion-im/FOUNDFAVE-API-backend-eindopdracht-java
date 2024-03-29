package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.services.FileService;
import online.foundfave.foundfaveapi.utilities.RandomStringGenerator;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;

@CrossOrigin
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // To get a profile image from a certain profile, look in the ProfileController and its getProfileImageByUsername method
    @GetMapping("/download-profile-image/{fileName}")
    public ResponseEntity<Object> downloadProfileImage(@PathVariable String fileName) {
        Resource resource = fileService.downloadImage(fileName);
        MediaType contentType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @GetMapping("/download-character-image/{fileName}")
    public ResponseEntity<Object> downloadCharacterImage(@PathVariable String fileName) {
        Resource resource = fileService.downloadImage(fileName);
        MediaType contentType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @GetMapping("/download-movie-image/{fileName}")
    public ResponseEntity<Object> downloadMovieImage(@PathVariable String fileName) {
        Resource resource = fileService.downloadImage(fileName);
        MediaType contentType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @PostMapping("/upload-profile-image/{profileId}")
    public ResponseEntity<Object> uploadProfileImageById(@PathVariable Long profileId, @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(RandomStringGenerator.generateAlphaNumeric(5).toLowerCase() + file.getOriginalFilename());
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download-profile-image/").path(Objects.requireNonNull(fileName)).toUriString();
        fileService.uploadProfileImageById(file, url, profileId, fileName);
        return ResponseEntity.ok(url);
    }

    @PostMapping("/upload-character-image/{characterId}")
    public ResponseEntity<Object> uploadCharacterImageById(@PathVariable Long characterId, @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(RandomStringGenerator.generateAlphaNumeric(5).toLowerCase() + file.getOriginalFilename());
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download-character-image/").path(Objects.requireNonNull(fileName)).toUriString();
        fileService.uploadCharacterImageById(file, url, characterId, fileName);
        return ResponseEntity.ok(url);
    }

    @PostMapping("/upload-movie-image/{movieId}")
    public ResponseEntity<Object> uploadMovieImageById(@PathVariable Long movieId, @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(RandomStringGenerator.generateAlphaNumeric(5).toLowerCase() + file.getOriginalFilename());
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download-movie-image/").path(Objects.requireNonNull(fileName)).toUriString();
        fileService.uploadMovieImageById(file, url, movieId, fileName);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete-profile-image/{profileId}")
    public ResponseEntity<Object> deleteProfileImageById(@PathVariable Long profileId) {
        fileService.deleteProfileImageById(profileId);
        return ResponseEntity.ok().body("Profile image of the profile with id: " + profileId + " successfully deleted!");
    }

    @DeleteMapping("/delete-character-image/{characterId}")
    public ResponseEntity<Object> deleteCharacterImageById(@PathVariable Long characterId) {
        fileService.deleteCharacterImageById(characterId);
        return ResponseEntity.ok().body("Character image of the character with id: " + characterId + " successfully deleted!");
    }

    @DeleteMapping("/delete-movie-image/{movieId}")
    public ResponseEntity<Object> deleteMovieImageById(@PathVariable Long movieId) {
        fileService.deleteMovieImageById(movieId);
        return ResponseEntity.ok().body("Movie image of the movie with id: " + movieId + " successfully deleted!");
    }
}
