package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.IdInputDto;
import online.foundfave.foundfaveapi.dtos.input.PasswordInputDto;
import online.foundfave.foundfaveapi.dtos.input.UpdateUserInputDto;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.models.Character;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        List<UserOutputDto> userOutputDtoList = userService.getAllUsers();
        return ResponseEntity.ok().body(userOutputDtoList);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable("username") String username) {
        UserOutputDto userOutputDto = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(userOutputDto);
    }

    @PostMapping("")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        String newUsername = userService.createUser(userInputDto);
        userService.addAuthorityToCreatedUser(newUsername, "ROLE_USER");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newUsername).toUriString());
        return ResponseEntity.created(uri).body("Username: " + "'" + newUsername + "'" + " registered successfully!");
    }

    @PutMapping("/admin/{username}")
    public ResponseEntity<Object> updateUserPasswordAdmin(@PathVariable("username") String username, @Valid @RequestBody PasswordInputDto passwordInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        userService.updateUserPasswordAdmin(username, passwordInputDto);
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " password updated!");
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<String> updateUserDetails(@PathVariable("username") String username, @Valid @RequestBody UpdateUserInputDto updateUserInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        return ResponseEntity.ok(userService.updateUserDetails(username, updateUserInputDto));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body("Username: " + "'" + username + "'" + " deleted!");
    }

    @GetMapping("/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping("/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        String authorityName = (String) fields.get("authority");
        if (authorityName == null || !authorityName.equals("ROLE_ADMIN")) {
            throw new BadRequestException("Please fill in: ROLE_ADMIN!");
        }
        userService.addUserAuthority(username, authorityName);
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " has been promoted to ROLE_ADMIN!");
    }

    @DeleteMapping("/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " has been demoted to ROLE_USER!");
    }

    @GetMapping("/exists/{username}")
    public ResponseEntity<Object> doesUserExist(@PathVariable("username") String username) {
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " exists: " + userService.userExists(username));
    }

    @GetMapping("/search")
    public ResponseEntity<UserOutputDto> findUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserOutputDto>> findUserByEmailContains(@RequestParam("email") String email) {
        List<UserOutputDto> userOutputDtoList = userService.findUserByEmailContains(email);
        return ResponseEntity.ok(userOutputDtoList);
    }

    @GetMapping("/search/username")
    public ResponseEntity<List<UserOutputDto>> findUserByUsernameContains(@RequestParam("username") String username) {
        List<UserOutputDto> userOutputDtoList = userService.findUserByUsernameContains(username);
        return ResponseEntity.ok(userOutputDtoList);
    }

    @GetMapping("/active")
    public List<UserOutputDto> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @PutMapping("/assign/profile/{username}")
    public ResponseEntity<Object> assignProfileToUser(@PathVariable("username") String username, @Valid @RequestBody IdInputDto idInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        userService.assignProfileToUser(username, idInputDto.id);
        return ResponseEntity.ok().body("User with username: " + "'" + username + "'" + " now assigned to profile with id: " + idInputDto.id + ".");
    }

    @PutMapping("/detach/profile/{username}")
    public ResponseEntity<Object> detachProfileFromToUser(@PathVariable("username") String username) {
        userService.detachProfileFromToUser(username);
        return ResponseEntity.ok().body("User with username: " + "'" + username + "'" + " now detached from its profile!");
    }

    @GetMapping("/favorites/{username}")
    public ResponseEntity<List<Character>> getAllFavoritesFromUser(@PathVariable("username") String username) {
        UserOutputDto userOutputDto = userService.getAllFavoritesFromUser(username);
        return ResponseEntity.ok().body(userOutputDto.favoritesList);
    }

    @PutMapping("/add/character/{username}")
    public ResponseEntity<Object> addFavoriteCharacterToUser(@PathVariable("username") String username, @Valid @RequestBody IdInputDto idInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        userService.addFavoriteCharacterToUser(username, idInputDto.id);
        return ResponseEntity.ok().body("Character with id: " + idInputDto.id + " now a favorite of user with username: " + "'" + username + "'" + ".");
    }

    @DeleteMapping("/remove/character/{username}")
    public ResponseEntity<Object> removeFavoriteCharacterFromUser(@PathVariable("username") String username, @Valid @RequestBody IdInputDto idInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        userService.removeFavoriteCharacterFromUser(username, idInputDto.id);
        return ResponseEntity.ok().body("Character with id: " + idInputDto.id + " is no longer a favorite of user with username: " + "'" + username + "'" + ".");
    }
}