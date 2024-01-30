package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.CharacterNotFoundException;
import online.foundfave.foundfaveapi.exceptions.MovieNotFoundException;
import online.foundfave.foundfaveapi.exceptions.ProfileNotFoundException;
import online.foundfave.foundfaveapi.models.Character;
import online.foundfave.foundfaveapi.models.Movie;
import online.foundfave.foundfaveapi.models.Profile;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import online.foundfave.foundfaveapi.repositories.MovieRepository;
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
    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;

    public FileService(@Value("${my.upload_location}") String fileStorageLocation, ProfileRepository profileRepository, CharacterRepository characterRepository, MovieRepository movieRepository) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.profileRepository = profileRepository;
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException ioe) {
            throw new RuntimeException("An error occurred while creating the file directory!");
        }
    }

    public Resource downloadImage(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException mue) {
            throw new RuntimeException("There is an error reading the file.", mue);
        }
        if (!resource.exists() || !resource.isReadable()) {
            throw new BadRequestException("The file does not exist or is not readable.");
        }
        return resource;
    }

    public void uploadProfileImageById(MultipartFile file, String url, Long profileId, String fileName) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException("Profile with id: " + profileId + " not found!"));
        if (profile.getProfileImageUrl() != null) {
            Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(profile.getFileName());
            try {
                Files.deleteIfExists(path);
            } catch (IOException ioe) {
                throw new RuntimeException("An error occurred while deleting: " + profile.getFileName());
            }
        }
        storeFile(file, fileName);
        profile.setProfileImageUrl(url);
        profile.setFileName(fileName);
        profileRepository.save(profile);
    }

    public void uploadCharacterImageById(MultipartFile file, String url, Long characterId, String fileName) {
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character with id: " + characterId + " not found!"));
        if (character.getCharacterImageUrl() != null) {
            Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(character.getFileName());
            try {
                Files.deleteIfExists(path);
            } catch (IOException ioe) {
                throw new RuntimeException("An error occurred while deleting: " + character.getFileName());
            }
        }
        storeFile(file, fileName);
        character.setCharacterImageUrl(url);
        character.setFileName(fileName);
        characterRepository.save(character);
    }

    public void uploadMovieImageById(MultipartFile file, String url, Long movieId, String fileName) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie with id: " + movieId + " not found!"));
        if (movie.getMovieImageUrl() != null) {
            Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(movie.getFileName());
            try {
                Files.deleteIfExists(path);
            } catch (IOException ioe) {
                throw new RuntimeException("An error occurred while deleting: " + movie.getFileName());
            }
        }
        storeFile(file, fileName);
        movie.setMovieImageUrl(url);
        movie.setFileName(fileName);
        movieRepository.save(movie);
    }

    public void deleteProfileImageById(Long profileId) {
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
        } catch (IOException ioe) {
            throw new RuntimeException("An error occurred while deleting: " + profile.getFileName());
        }
    }

    public void deleteCharacterImageById(Long characterId) {
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character with id: " + characterId + " not found!"));
        if (character.getCharacterImageUrl() == null) {
            throw new BadRequestException("This character does not have an image!");
        }
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(character.getFileName());
        character.setCharacterImageUrl(null);
        character.setFileName(null);
        characterRepository.save(character);
        try {
            Files.deleteIfExists(path);
        } catch (IOException ioe) {
            throw new RuntimeException("An error occurred while deleting: " + character.getFileName());
        }
    }

    public void deleteMovieImageById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie with id: " + movieId + " not found!"));
        if (movie.getMovieImageUrl() == null) {
            throw new BadRequestException("This movie does not have an image!");
        }
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(movie.getFileName());
        movie.setMovieImageUrl(null);
        movie.setFileName(null);
        movieRepository.save(movie);
        try {
            Files.deleteIfExists(path);
        } catch (IOException ioe) {
            throw new RuntimeException("An error occurred while deleting: " + movie.getFileName());
        }
    }

    public void storeFile(MultipartFile file, String fileName) {
        Path filePath = Paths.get(fileStoragePath + File.separator + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new RuntimeException("There is an error storing the file.", ioe);
        }
    }
}
