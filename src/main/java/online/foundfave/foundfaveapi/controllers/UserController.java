package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.UsernameNotFoundException;
import online.foundfave.foundfaveapi.services.UserService;
import online.foundfave.foundfaveapi.utils.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("#username == authentication.principal.username")
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

    // Strip both ROLE_ADMIN and/or ROLE_USER from a user
    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " has been demoted!");
    }

    @GetMapping(value = "/exists/{username}")
    public ResponseEntity<Object> doesUserExist(@PathVariable("username") String username) {
        return ResponseEntity.ok().body("Username: " + "'" + username + "'" + " exists: " + userService.userExists(username));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<UserOutputDto> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Add character to Favorites list
//    @PutMapping("/televisions/{id}/{ciModuleId}")
//    public ResponseEntity<Object> assignCIModuleToTelevision(@PathVariable("id") Long id, @PathVariable("ciModuleId") Long ciModuleId) {
//        televisionService.assignCIModuleToTelevision(id, ciModuleId);
//        return ResponseEntity.noContent().build();
//    }

//    @PutMapping("/users/{username}/{characterId}")
//    public ResponseEntity<Object> addCharacterToFavorites(@PathVariable("username") String username, @PathVariable("characterId") Long characterId) {
//        userService.addCharacterToFavorites(username, characterId);
//        return ResponseEntity.noContent().build();
//    }




    // Deze methode is om alle wallbrackets op te halen die aan een bepaalde television gekoppeld zijn.
    // Deze methode maakt gebruik van de televisionWallBracketService.
//    @GetMapping("/televisions/wallBrackets/{televisionId}")
//    public ResponseEntity<Collection<WallBracketDto>> getWallBracketsByTelevisionId(@PathVariable("televisionId") Long televisionId) {
//        return ResponseEntity.ok(televisionWallBracketService.getWallBracketsByTelevisionId(televisionId));
//    }










}