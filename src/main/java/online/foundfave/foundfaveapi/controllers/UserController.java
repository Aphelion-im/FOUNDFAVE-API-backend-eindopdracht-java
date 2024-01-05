package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.UsernameNotFoundException;
import online.foundfave.foundfaveapi.services.UserService;
import online.foundfave.foundfaveapi.utils.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserOutputDto>> getUsers() {
        List<UserOutputDto> userOutputDtosCollection = userService.getUsers();
        return ResponseEntity.ok().body(userOutputDtosCollection);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String username) {
        UserOutputDto optionalUser = userService.getUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(br));
        }
        String newUsername = userService.createUser(userInputDto);
        userService.addAuthority(newUsername, "ROLE_USER");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newUsername).toUriString());
        return ResponseEntity.created(uri).body("User " + "'" + newUsername + "'" + " registered successfully!");
    }

    // TODO: User kan eigen password niet aanpassen
    @PutMapping(value = "/{username}")
    public ResponseEntity<Object> updateUserPassword(@PathVariable("username") String username, @RequestBody UserInputDto userInputDto) {
        userService.updateUserPassword(username, userInputDto);
        return ResponseEntity.ok().body("User " + "'" + username + "'" + " password updated!");
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body("Username: " + "'" + username + "'" + " deleted!");
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    // Promote user to Admin role
    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.ok().body("User: " + "'" + username + "'" + " has been promoted!");
        } catch (Exception ex) {
            throw new UsernameNotFoundException("User with id: " + "'" + username + "'" + " not found!");
        }
    }

    // Strip both ROLE_ADMIN and/or ROLE_USER from a user
    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.ok().body("User: " + "'" + username + "'" + " has been demoted!");
    }

    // Klaar
    @GetMapping(value = "/exists/{username}")
    public ResponseEntity<Object> doesUserExist(@PathVariable("username") String username) {
        return ResponseEntity.ok().body("User " + "'" + username + "'" + " exists: " + userService.userExists(username));
    }

    // Klaar
    @GetMapping(value = "/search")
    public ResponseEntity<UserOutputDto> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

}