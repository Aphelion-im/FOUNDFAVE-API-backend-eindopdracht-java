package online.foundfave.foundfaveapi.services;

import jakarta.validation.constraints.NotNull;
import online.foundfave.foundfaveapi.dtos.UserDto;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.RecordNotFoundException;
import online.foundfave.foundfaveapi.exceptions.UserAlreadyExistsException;
import online.foundfave.foundfaveapi.models.Authority;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.UserRepository;
import online.foundfave.foundfaveapi.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            collection.add(TransformUserToOutputDto(user));
        }
        return collection;
    }

    public UserOutputDto getUser(String username) {
        UserOutputDto outputDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            outputDto = TransformUserToOutputDto(user.get());
        } else {
            throw new RecordNotFoundException("Username " + "'" + username + "'" + " not found!");
        }
        return outputDto;
    }

    // This method is used for the CustomUserDetailsService class
    public UserInputDto getUserWithPassword(String username) {
        UserInputDto userInputDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userInputDto = TransformUserToInputDto(user.get());
        } else {
            throw new RecordNotFoundException("Username: " + "'" + username + "'" + " not found!");
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
        userInputDto.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        User newUser = userRepository.save(TransformUserInputDtoToUser(userInputDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        if (Objects.equals(username, "admin")) {
            throw new BadRequestException("You are not allowed to delete the 'admin' account!");
        }
        if (!userRepository.existsById(username)) {
            throw new RecordNotFoundException("User with id: " + "'" + username + "'" + " not found!");
        }
        userRepository.deleteById(username);
    }

    public void updateUserPassword(String username, UserInputDto userInputDto) {
        if (!userRepository.existsById(username))
            throw new RecordNotFoundException("User with id: " + "'" + username + "'" + " not found!");
        User user = userRepository.findById(username).orElse(null);
        assert user != null;
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).orElse(null);
        assert user != null;
        UserOutputDto outputDto = TransformUserToOutputDto(user);
        return outputDto.getAuthorities();
    }


    // TODO: Herschrijven met isPresent()
    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    // TODO: Herschrijven met isPresent()
    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    // Klaar
    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    // Klaar
    public UserOutputDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User not found with e-mail: " + "'" + email + "'."));
        return TransformUserToOutputDto(user);
    }


    // TODO: Herschrijven
    // Mappers oud
//    public static UserDto fromUser(User user) {
//
//        var dto = new UserDto();
//
//        dto.username = user.getUsername();
//        dto.password = user.getPassword();
//        dto.enabled = user.isEnabled();
//        dto.apikey = user.getApikey();
//        dto.email = user.getEmail();
//        dto.authorities = user.getAuthorities();
//
//        return dto;
//    }

//    public User toUser(UserDto userDto) {
//
//        var user = new User();
//
//        user.setUsername(userDto.getUsername());
//        user.setPassword(userDto.getPassword());
//        user.setEnabled(userDto.getEnabled());
//        user.setApikey(userDto.getApikey());
//        user.setEmail(userDto.getEmail());
//
//        return user;
//    }


    // Mappers advanced
    public static UserOutputDto TransformUserToOutputDto(User user) {
        var userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.enabled = user.isEnabled();
        userOutputDto.email = user.getEmail();
        userOutputDto.authorities = user.getAuthorities();
        return userOutputDto;
    }


    public User TransformUserInputDtoToUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        user.setApikey(userInputDto.getApikey());
        user.setEmail(userInputDto.getEmail());
        return user;
    }

    // From User to InputDto
    public static UserInputDto TransformUserToInputDto(User user) {
        var userInputDto = new UserInputDto();
        userInputDto.username = user.getUsername();
        userInputDto.password = user.getPassword();
        userInputDto.apikey = user.getApikey();
        userInputDto.email = user.getEmail();
        userInputDto.authorities = user.getAuthorities();
        return userInputDto;
    }


}