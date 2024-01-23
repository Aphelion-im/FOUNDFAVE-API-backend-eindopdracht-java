package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.PasswordInputDto;
import online.foundfave.foundfaveapi.dtos.input.UpdateUserInputDto;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.*;
import online.foundfave.foundfaveapi.models.Authority;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import online.foundfave.foundfaveapi.repositories.ProfileRepository;
import online.foundfave.foundfaveapi.repositories.UserRepository;
import online.foundfave.foundfaveapi.utilities.RandomStringGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CharacterRepository characterRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository, CharacterRepository characterRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.characterRepository = characterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Basic CRUD methods
    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> userOutputDtoList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            userOutputDtoList.add(transformUserToUserOutputDto(user));
        }
        return userOutputDtoList;
    }

    public UserOutputDto getUser(String username) {
        return getUserOutputDto(username);
    }

    public String createUser(UserInputDto userInputDto) {
        Optional<User> user = userRepository.findById(userInputDto.username);
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("Username: " + "'" + userInputDto.username + "'" + " already exists!");
        }
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDto.setApikey(randomString);
        User newUser = userRepository.save(transformUserInputDtoToUser(userInputDto));
        return newUser.getUsername();
    }

    public void updateUserPasswordAdmin(String username, PasswordInputDto passwordInputDto) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        user.setPassword(passwordEncoder.encode(passwordInputDto.getPassword()));
        userRepository.save(user);
    }

    public String updateUser(String username, UpdateUserInputDto updateUserInputDto) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        if (updateUserInputDto.password != null)
            user.setPassword(passwordEncoder.encode(updateUserInputDto.getPassword()));
        if (updateUserInputDto.apikey != null) user.setApikey(updateUserInputDto.getApikey());
        if (updateUserInputDto.email != null) user.setEmail(updateUserInputDto.getEmail());
        userRepository.save(user);
        return "User: " + "'" + username + "'" + " updated successfully!";
    }

    public void deleteUser(String username) {
        if (Objects.equals(username, "admin")) {
            throw new BadRequestException("You are not allowed to delete the 'admin' account!");
        }
        if (!userRepository.existsById(username)) {
            throw new UsernameNotFoundException("User with id: " + "'" + username + "'" + " not found!");
        }
        try {
            userRepository.deleteById(username);
        } catch (Exception e) {
            throw new BadRequestException("You are not allowed to delete this user as it still linked to a contact form!");
        }
    }

    public Set<Authority> getAuthorities(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        UserOutputDto outputDto = transformUserToUserOutputDto(user);
        return outputDto.getAuthorities();
    }

    // This method works with the createUser method and is not associated with a controller.
    public void addAuthorityToCreatedUser(String username, String authority) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    // This method works with the addUserAuthority method in UserController
    public void addUserAuthority(String username, String authority) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (Objects.equals(username, "admin")) {
            throw new BadRequestException("You are not allowed to demote this user!");
        }
        if (!Objects.equals(authority, "ROLE_ADMIN")) {
            throw new BadRequestException("ROLE_USER can not be removed. Please, fill in this role: ROLE_ADMIN!");
        }
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().orElse(null);
        if (!user.getAuthorities().contains(authorityToRemove)) {
            throw new BadRequestException("This user is already (demoted to) ROLE_USER!");
        }
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    // Repository methods
    public UserOutputDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("User not found with e-mail: " + "'" + email + "'."));
        return transformUserToUserOutputDto(user);
    }

    public List<UserOutputDto> findUserByEmailContains(String email) {
        List<UserOutputDto> userOutputDtoList = new ArrayList<>();
        List<User> userList = userRepository.findByEmailContainsIgnoreCase(email);
        for (User user : userList) {
            userOutputDtoList.add(transformUserToUserOutputDto(user));
        }
        if (userOutputDtoList.isEmpty()) {
            throw new UsernameNotFoundException("0 results. No users were found!");
        }
        return userOutputDtoList;
    }

    public List<UserOutputDto> findUserByUsernameContains(String username) {
        List<UserOutputDto> userOutputDtoList = new ArrayList<>();
        List<User> userList = userRepository.findByUsernameContainsIgnoreCase(username);
        for (User user : userList) {
            userOutputDtoList.add(transformUserToUserOutputDto(user));
        }
        if (userOutputDtoList.isEmpty()) {
            throw new UsernameNotFoundException("0 results. No users were found!");
        }
        return userOutputDtoList;
    }

    public List<UserOutputDto> getActiveUsers() {
        List<User> activeUsersList = userRepository.findByEnabled(true);
        List<UserOutputDto> activeUsersOutputDtoList = new ArrayList<>();
        for (User user : activeUsersList) {
            activeUsersOutputDtoList.add(transformUserToUserOutputDto(user));
        }
        return activeUsersOutputDtoList;
    }

    // Relational methods
    public void assignProfileToUser(String username, Long profileId) {
        var optionalUser = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        var optionalProfile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + profileId + "!"));
        if (optionalUser.getProfile() != null) {
            throw new BadRequestException("This user is already assigned to a profile.");
        }
        optionalUser.setProfile(optionalProfile);
        userRepository.save(optionalUser);
    }

    public void detachProfileFromToUser(String username) {
        var optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            if (user.getProfile() == null) {
                throw new BadRequestException("This profile has already been detached from its user!");
            } else {
                user.setProfile(null);
                userRepository.save(user);
            }
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    // TODO: Attach ContactForm to user
    // TODO: Detach Contactform from user

    public UserOutputDto getAllFavoritesFromUser(String username) {
        return getUserOutputDto(username);
    }

    // Add character to favorites
    public void addFavoriteCharacterToUser(String username, Long characterId) {
        var optionalCharacter = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        var optionalUser = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        if (optionalUser.getFavoritesList().contains(optionalCharacter)) {
            throw new BadRequestException("This character is already on this list.");
        } else {
            optionalUser.getFavoritesList().add(optionalCharacter);
            var updatedOptionalUser = userRepository.save(optionalUser);
            transformUserToUserOutputDto(updatedOptionalUser);
        }
    }

    // Remove character from favorites
    public void removeFavoriteCharacterFromUser(String username, Long characterId) {
        var optionalCharacter = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        var optionalUser = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        if (!optionalUser.getFavoritesList().contains(optionalCharacter)) {
            throw new BadRequestException("This character is not on this list or was already removed.");
        } else {
            optionalUser.getFavoritesList().remove(optionalCharacter);
            var updatedOptionalUser = userRepository.save(optionalUser);
            transformUserToUserOutputDto(updatedOptionalUser);
        }
    }

    // This method is used for the CustomUserDetailsService class
    public UserInputDto getUserByUsername(String username) {
        UserInputDto userInputDto;
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            userInputDto = transformUserToUserInputDto(optionalUser.get());
        } else {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
        return userInputDto;
    }

    private UserOutputDto getUserOutputDto(String username) {
        UserOutputDto userOutputDto;
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            userOutputDto = transformUserToUserOutputDto(optionalUser.get());
        } else {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
        return userOutputDto;
    }

    // Transformers
    // User to UserOutputDto
    public static UserOutputDto transformUserToUserOutputDto(User user) {
        var userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.enabled = user.isEnabled();
        userOutputDto.email = user.getEmail();
        userOutputDto.authorities = user.getAuthorities();
        userOutputDto.profile = user.getProfile();
        userOutputDto.favoritesList = user.getFavoritesList();
        userOutputDto.contactFormsList = user.getContactFormsList();
        return userOutputDto;
    }

    // From UserInputDto to User
    public User transformUserInputDtoToUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        user.setApikey(userInputDto.getApikey());
        user.setEmail(userInputDto.getEmail());
        return user;
    }

    // From User to UserInputDto
    public static UserInputDto transformUserToUserInputDto(User user) {
        var userInputDto = new UserInputDto();
        userInputDto.username = user.getUsername();
        userInputDto.password = user.getPassword();
        userInputDto.apikey = user.getApikey();
        userInputDto.email = user.getEmail();
        userInputDto.authorities = user.getAuthorities();
        return userInputDto;
    }
}