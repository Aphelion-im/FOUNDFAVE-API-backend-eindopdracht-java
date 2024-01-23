package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.ProfileNotFoundException;
import online.foundfave.foundfaveapi.models.Profile;
import online.foundfave.foundfaveapi.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Value("${my.upload_location}")
    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final ProfileRepository profileRepository;

    public FileService(@Value("${my.upload_location}") String fileStorageLocation, ProfileRepository profileRepository) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.profileRepository = profileRepository;
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("There is an issue creating the file directory!");
        }

    }

    public Resource downloadImage(String fileName) {
        return downloadPicture(fileName);
    }

    // TODO: Authentiation check
    public void uploadProfileImage(MultipartFile file, String url, Long profileId, String fileName) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException("Profile with id: " + profileId + " not found!"));
        if (profile.getProfileImageUrl() != null) {
            Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(profile.getFileName());
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException("A problem occurred while deleting: " + profile.getFileName());
            }
        }
        storeFile(file, fileName);
        profile.setProfileImageUrl(url);
        profile.setFileName(fileName);
        profileRepository.save(profile);
    }

    // TODO: Authentication check
    public void deleteProfileImage(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException("Profile with id: " + profileId + " not found!"));
        if (profile.getProfileImageUrl() == null) {
            throw new BadRequestException("This profile does not have a profile image!");
        }
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(profile.getFileName());
        profile.setProfileImageUrl(null);
        profile.setFileName(null);
        profileRepository.save(profile);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("A problem occurred with deleting: " + profile.getFileName());
        }
    }

    public void storeFile(MultipartFile file, String fileName) {
        Path filePath = Paths.get(fileStoragePath + File.separator + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
    }

    public Resource downloadPicture(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException mue) {
            throw new RuntimeException("Issue in reading the file", mue);
        }
        if (!resource.exists() || !resource.isReadable()) {
            throw new BadRequestException("The file doesn't exist or is not readable.");
        }
        return resource;
    }
}
