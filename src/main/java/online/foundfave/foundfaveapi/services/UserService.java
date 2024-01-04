package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.UserDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.RecordNotFoundException;
import online.foundfave.foundfaveapi.models.Authority;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.UserRepository;
import online.foundfave.foundfaveapi.utils.RandomStringGenerator;
import org.springframework.beans.BeanUtils;
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

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = fromUser(user.get());
        } else {
            throw new RecordNotFoundException("Username " + "'" + username + "'" + " not found!");
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) throws RecordNotFoundException, BadRequestException {
        if (Objects.equals(username, "admin")) {
            throw new BadRequestException("You are not allowed to delete the 'admin' account!");
        }
        if (!userRepository.existsById(username)) {
            throw new RecordNotFoundException("User with id: " + "'" + username + "'" + " not found!");
        }
        userRepository.deleteById(username);
    }

    // TODO: isPresent()
    public void updateUserPassword(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();

        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
    }

    // TODO: Herschrijven met isPresent()
    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
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

    public UserOutputDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User not found with e-mail: " + "'" + email + "'."));
        return transferToOutputDto(user);
    }


    // TODO: Herschrijven
    // Mappers
    public static UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());

        return user;
    }


    // Mappers advanced
    public UserOutputDto transferToOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.enabled = user.isEnabled();
        userOutputDto.apikey = user.getApikey();
        userOutputDto.email = user.getEmail();
        userOutputDto.authorities = user.getAuthorities();
        return userOutputDto;
    }



}