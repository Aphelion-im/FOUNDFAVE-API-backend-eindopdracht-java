package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.IdInputDto;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.UsernameNotFoundException;
import online.foundfave.foundfaveapi.services.UserService;
import online.foundfave.foundfaveapi.utilities.FieldErrorHandling;
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

    // Basic CRUD methods
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

    // TODO: Everytime a user is created, create a corresponding profile with the same id. Geef standaardwaarden mee zoals John doe, johndoe@email.com, etc.
    // TODO: createAccountWithProfile, withoutProfile
    @PostMapping(value = "")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        String newUsername = userService.createUser(userInputDto);
        userService.addAuthority(newUsername, "ROLE_USER");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newUsername).toUriString());
        return ResponseEntity.created(uri).body("Username: " + "'" + newUsername + "'" + " registered successfully!");
    }

    // TODO: User kan eigen password niet aanpassen
    // Admin only. Admin is allowed to overwrite passwords from other users.
    @PutMapping(value = "/admin/{username}")
    public ResponseEntity<Object> updateUserPassword(@PathVariable("username") String username, @RequestBody UserInputDto userInputDto) {
        userService.updateUserPassword(username, userInputDto);
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " password updated!");
    }

    // TODO: Validatie mogelijk op een put request?
    // TODO: Betere manier vinden om andere gebruikers te beschermen tegen het aanpassen van hun gegevens door een gebruiker
    // User is not allowed to overwrite passwords from other users.
    @PutMapping(value = "/user/{username}")
    public ResponseEntity<String> updateUser(@PathVariable("username") String username, @RequestBody UserInputDto userInputDto) {
        return ResponseEntity.ok(userService.updateUser(username, userInputDto));
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

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " has been promoted!");
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Username: " + "'" + username + "'" + " not found!");
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " has been demoted!");
    }

    @GetMapping(value = "/exists/{username}")
    public ResponseEntity<Object> doesUserExist(@PathVariable("username") String username) {
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " exists: " + userService.userExists(username));
    }

    // Repository methods
    @GetMapping(value = "/search")
    public ResponseEntity<UserOutputDto> findUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserOutputDto>> findUserByEmailContains(@RequestParam("email") String email) {
        List<UserOutputDto> users = userService.findUserByEmailContains(email);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/username")
    public ResponseEntity<List<UserOutputDto>> findUserByUsernameContains(@RequestParam("username") String username) {
        List<UserOutputDto> users = userService.findUserByUsernameContains(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active")
    public List<UserOutputDto> getActiveUsers() {
        return userService.getActiveUsers();
    }

    // Relational methods
    @PutMapping("/{username}/profile")
    public ResponseEntity<Object> assignProfileToUser(@PathVariable("username") String username, @Valid @RequestBody IdInputDto idInputDto) {
        userService.assignProfileToUser(username, idInputDto.id);
        return ResponseEntity.ok().body("User with username: " + "'" + username + "'" + " now coupled with profile with id: " + idInputDto.id + ".");
    }


// TODO: Add character to Favorites
// TODO: Get all favorites from username
// TODO: Delete favorite

    // Image methods
}