package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.ProfileInputDto;
import online.foundfave.foundfaveapi.dtos.output.ProfileOutputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.ProfileAlreadyExistsException;
import online.foundfave.foundfaveapi.exceptions.ProfileNotFoundException;
import online.foundfave.foundfaveapi.exceptions.UsernameNotFoundException;
import online.foundfave.foundfaveapi.models.Profile;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.ProfileRepository;
import online.foundfave.foundfaveapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static online.foundfave.foundfaveapi.services.UserService.transformUserToUserOutputDto;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    // Basic CRUD methods
    public List<ProfileOutputDto> getProfiles() {
        List<ProfileOutputDto> profileOutputDtoList = new ArrayList<>();
        List<Profile> profileList = profileRepository.findAll();
        for (Profile profile : profileList) {
            profileOutputDtoList.add(transformProfileToProfileOutputDto(profile));
        }
        return profileOutputDtoList;
    }

    public ProfileOutputDto getProfileById(Long profileId) {
        ProfileOutputDto profileOutputDto;
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (optionalProfile.isPresent()) {
            profileOutputDto = transformProfileToProfileOutputDto(optionalProfile.get());
        } else {
            throw new ProfileNotFoundException("Profile with id: " + profileId + " not found!");
        }
        return profileOutputDto;
    }

    public Long createProfile(ProfileInputDto profileInputDto) {
        Optional<Profile> optionalProfile = profileRepository.findByFirstNameAndLastName(profileInputDto.firstName, profileInputDto.lastName);
        if (optionalProfile.isPresent()) {
            throw new ProfileAlreadyExistsException("Profile: " + "'" + profileInputDto.firstName + " " + profileInputDto.lastName + "'" + " already exists!");
        }
        Profile newProfile = profileRepository.save(transformProfileInputDtoToProfile(profileInputDto));
        return newProfile.getProfileId();
    }

    public void updateProfile(Long profileId, ProfileInputDto profileInputDto) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + profileId + "!"));
        if (profileInputDto.firstName != null) profile.setFirstName(profileInputDto.getFirstName());
        if (profileInputDto.lastName != null) profile.setLastName(profileInputDto.getLastName());
        if (profileInputDto.gender != null) profile.setGender(profileInputDto.getGender());
        if (profileInputDto.dateOfBirth != null) profile.setDateOfBirth(profileInputDto.getDateOfBirth());
        if (profileInputDto.profileImageUrl != null) profile.setProfileImageUrl(profileInputDto.getProfileImageUrl());
        profileRepository.save(profile);
    }

    // Currently, profiles assigned to users, are not allowed to be deleted. First use the detachProfileFromUser method and then try to assign a new profile.
    public void deleteProfile(Long profileId) {
        if (!profileRepository.existsById(profileId)) {
            throw new ProfileNotFoundException("Profile with id: " + profileId + " not found!");
        }
        try {
            profileRepository.deleteById(profileId);
        } catch (Exception e) {
            throw new BadRequestException("You are not allowed to delete this profile as it belongs to a user!");
        }
    }

    // Repository methods
    public List<ProfileOutputDto> findProfileByFirstNameContains(String firstname) {
        List<ProfileOutputDto> profileOutputDtoList = new ArrayList<>();
        List<Profile> profileList = profileRepository.findByFirstNameContainsIgnoreCase(firstname);
        for (Profile profile : profileList) {
            profileOutputDtoList.add(transformProfileToProfileOutputDto(profile));
        }
        if (profileOutputDtoList.isEmpty()) {
            throw new ProfileNotFoundException("0 results. No profiles were found!");
        }
        return profileOutputDtoList;
    }

    public List<ProfileOutputDto> findProfileByLastNameContains(String lastname) {
        List<ProfileOutputDto> profileOutputDtoList = new ArrayList<>();
        List<Profile> profileList = profileRepository.findByLastNameContainsIgnoreCase(lastname);
        for (Profile profile : profileList) {
            profileOutputDtoList.add(transformProfileToProfileOutputDto(profile));
        }
        if (profileOutputDtoList.isEmpty()) {
            throw new ProfileNotFoundException("0 results. No profiles were found!");
        }
        return profileOutputDtoList;
    }

    // Relational methods
    public ProfileOutputDto getProfileByUsername(String username) {
        UserOutputDto userOutputDto;
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            userOutputDto = transformUserToUserOutputDto(optionalUser.get());
        } else {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
        if (userOutputDto.getProfile() == null) {
            throw new BadRequestException("This user has no profile!");
        }
        return transformProfileToProfileOutputDto(userOutputDto.getProfile());
    }

    // Image methods


    // Transformers
    // Profile to ProfileOutputDto
    public static ProfileOutputDto transformProfileToProfileOutputDto(Profile profile) {
        var profileOutputDto = new ProfileOutputDto();
        profileOutputDto.profileId = profile.getProfileId();
        profileOutputDto.firstName = profile.getFirstName();
        profileOutputDto.lastName = profile.getLastName();
        profileOutputDto.gender = profile.getGender();
        profileOutputDto.dateOfBirth = profile.getDateOfBirth();
        profileOutputDto.profileImageUrl = profile.getProfileImageUrl();
        return profileOutputDto;
    }

    // From ProfileInputDto to Profile
    public Profile transformProfileInputDtoToProfile(ProfileInputDto profileInputDto) {
        var profile = new Profile();
        profile.setFirstName(profileInputDto.getFirstName());
        profile.setLastName(profileInputDto.getLastName());
        profile.setGender(profileInputDto.getGender());
        profile.setDateOfBirth(profileInputDto.getDateOfBirth());
        profile.setProfileImageUrl(profileInputDto.getProfileImageUrl());
        return profile;
    }
}
