package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.*;
import online.foundfave.foundfaveapi.models.Authority;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.UserRepository;
import online.foundfave.foundfaveapi.utilities.RandomStringGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Basic CRUD methods
    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(transformUserToUserOutputDto(user));
        }
        return collection;
    }

    public UserOutputDto getUser(String username) {
        UserOutputDto outputDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            outputDto = transformUserToUserOutputDto(user.get());
        } else {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
        return outputDto;
    }

    // TODO: Everytime a user is created, create a corresponding profile with the same id
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

    public void updateUserPassword(String username, UserInputDto userInputDto) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        userRepository.save(user);
    }

    // TODO: Geeft nog steeds een positief bericht na --zogenaamd-- overschrijven ander account
    public String updateUser(String username, UserInputDto userInputDto) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        if (userInputDto.password != null) user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        if (userInputDto.apikey != null) user.setApikey(userInputDto.getApikey());
        if (userInputDto.email != null) user.setEmail(userInputDto.getEmail());
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
        userRepository.deleteById(username);
    }

    public Set<Authority> getAuthorities(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        UserOutputDto outputDto = transformUserToUserOutputDto(user);
        return outputDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!Objects.equals(authority, "ROLE_USER") & !Objects.equals(authority, "ROLE_ADMIN")) {
            throw new BadRequestException("Please, fill in one of these roles: ROLE_ADMIN or ROLE_USER!");
        }
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().orElse(null);
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
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findByEmailContainsIgnoreCase(email);
        for (User user : list) {
            collection.add(transformUserToUserOutputDto(user));
        }
        if (collection.isEmpty()) {
            throw new UsernameNotFoundException("0 results. No users were found!");
        }
        return collection;
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

    // Image methods

    // This method is used for the CustomUserDetailsService class
    public UserInputDto getUserByUsername(String username) {
        UserInputDto userInputDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userInputDto = transformUserToUserInputDto(user.get());
        } else {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
        return userInputDto;
    }

    // Transformers
    // User to UserOutputDto
    public static UserOutputDto transformUserToUserOutputDto(User user) {
        var userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.enabled = user.isEnabled();
        userOutputDto.email = user.getEmail();
        userOutputDto.authorities = user.getAuthorities();
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