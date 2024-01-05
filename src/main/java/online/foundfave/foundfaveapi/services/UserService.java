package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.*;
import online.foundfave.foundfaveapi.models.Authority;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.UserRepository;
import online.foundfave.foundfaveapi.utils.RandomStringGenerator;
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

    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(TransformUserToUserOutputDto(user));
        }
        return collection;
    }

    public UserOutputDto getUser(String username) {
        UserOutputDto outputDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            outputDto = TransformUserToUserOutputDto(user.get());
        } else {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
        return outputDto;
    }

    // This method is used for the CustomUserDetailsService class
    public UserInputDto getUserWithPassword(String username) {
        UserInputDto userInputDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userInputDto = TransformUserToUserInputDto(user.get());
        } else {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
        return userInputDto;
    }

    public String createUser(UserInputDto userInputDto) {
        Optional<User> user = userRepository.findById(userInputDto.username);
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("Username: " + "'" + userInputDto.username + "'" + " already exists!");
        }
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDto.setApikey(randomString);
        User newUser = userRepository.save(TransformUserInputDtoToUser(userInputDto));
        return newUser.getUsername();
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

    public void updateUserPassword(String username, UserInputDto userInputDto) {
        User principalUser = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        principalUser.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        userRepository.save(principalUser);
    }

    // TODO: Geeft nog steeds een positief bericht na zogenaamd overschrijven ander account
    public String updateUser(String username, UserInputDto userInputDto) {
        User principalUser = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        String encodedPassword = passwordEncoder.encode(userInputDto.getPassword());
        principalUser.setPassword(encodedPassword);
        principalUser.setEmail(userInputDto.getEmail());
        userRepository.save(principalUser);

        return "User updated successfully!";
    }

    public Set<Authority> getAuthorities(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        UserOutputDto outputDto = TransformUserToUserOutputDto(user);
        return outputDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + "'" + username + "'!"));
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().orElse(null);
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public UserOutputDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("User not found with e-mail: " + "'" + email + "'."));
        return TransformUserToUserOutputDto(user);
    }

    // User to UserOutputDto
    public static UserOutputDto TransformUserToUserOutputDto(User user) {
        var userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.enabled = user.isEnabled();
        userOutputDto.email = user.getEmail();
        userOutputDto.authorities = user.getAuthorities();
        return userOutputDto;
    }

    // From UserInputDto to User
    public User TransformUserInputDtoToUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        user.setApikey(userInputDto.getApikey());
        user.setEmail(userInputDto.getEmail());
        return user;
    }

    // From User to UserInputDto
    public static UserInputDto TransformUserToUserInputDto(User user) {
        var userInputDto = new UserInputDto();
        userInputDto.username = user.getUsername();
        userInputDto.password = user.getPassword();
        userInputDto.apikey = user.getApikey();
        userInputDto.email = user.getEmail();
        userInputDto.authorities = user.getAuthorities();
        return userInputDto;
    }
}