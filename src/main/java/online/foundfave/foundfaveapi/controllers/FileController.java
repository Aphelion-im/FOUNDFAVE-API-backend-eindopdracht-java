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

    // TODO: /download character image
    // TODO: /download movie image
    // To get a profile image from a certain profile, look in the ProfileController getProfileImageByUsername method
    @GetMapping("/download-profile-image/{fileName}")
    public ResponseEntity<Object> downloadProfileImage(@PathVariable String fileName) {
        Resource resource = fileService.downloadImage(fileName);
        MediaType contentType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    // TODO: /upload character image
    // TODO: /upload movie image
    @PostMapping("/upload-profile-image/{profileId}")
    public ResponseEntity<Object> uploadProfileImage(@PathVariable Long profileId, @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(RandomStringGenerator.generateAlphaNumeric(5).toLowerCase() + file.getOriginalFilename());
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download-profile-image/").path(Objects.requireNonNull(fileName)).toUriString();
        fileService.uploadProfileImage(file, url, profileId, fileName);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete-profile-image/{profileId}")
    public ResponseEntity<Object> deleteProfileImageById(@PathVariable Long profileId) {
        fileService.deleteProfileImage(profileId);
        return ResponseEntity.ok().body("Profile image successfully deleted!");
    }
}
