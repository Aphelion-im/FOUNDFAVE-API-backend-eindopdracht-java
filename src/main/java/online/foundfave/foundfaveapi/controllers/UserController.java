package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.UserDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.RecordNotFoundException;
import online.foundfave.foundfaveapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Klaar
    @GetMapping(value = "")
    public ResponseEntity<List<UserOutputDto>> getUsers() {
        List<UserOutputDto> userOutputDtosCollection = userService.getUsers();
        return ResponseEntity.ok().body(userOutputDtosCollection);
    }

    // Klaar
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String username) {
        UserOutputDto optionalUser = userService.getUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }











//    // TODO: User already exists error: kan dubbele usernames aanmaken
//    @PostMapping(value = "")
//    public ResponseEntity<Object> createUser(@RequestBody UserDto dto) {
//        String newUsername = userService.createUser(dto);
//        userService.addAuthority(newUsername, "ROLE_USER");
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newUsername).toUriString());
//        return ResponseEntity.created(uri).body("User " + "'" + newUsername + "'" + " registered successfully!");
//    }

//    // TODO: InputDto en FieldErrorHandling
//    // TODO: Je kan verkeerde username invoeren (bijvoorbeeld admin ipv andre) en het geeft geen waarschuwing
//    // TODO: Kan iedereen zomaar een password wijzigen? Dit beveiligen?
//    @PutMapping(value = "/{username}")
//    public ResponseEntity<UserDto> updateUserPassword(@PathVariable("username") String username, @RequestBody UserDto dto) {
//        userService.updateUserPassword(username, dto);
//        return ResponseEntity.noContent().build(); // 204. TODO: Is er een ResponseEntity die wat specifieker is?
//    }

//    @DeleteMapping(value = "/{username}")
//    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) throws RecordNotFoundException, BadRequestException {
//        userService.deleteUser(username);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(username);
//    }

//    // TODO: Hoe kan deze worden verbeterd?
//    @GetMapping(value = "/{username}/authorities")
//    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
//        return ResponseEntity.ok().body(userService.getAuthorities(username));
//    }

//    // TODO: Geeft geen feedback na promotie van een user
//    // Promote to Admin role to user
//    @PostMapping(value = "/{username}/authorities")
//    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
//        try {
//            String authorityName = (String) fields.get("authority");
//            userService.addAuthority(username, authorityName);
//            return ResponseEntity.noContent().build(); // 204. TODO: Is er een ResponseEntity die wat specifieker is?
//        } catch (Exception ex) {
//            throw new BadRequestException();
//        }
//    }

//    // TODO: Geeft geen feedback na degraderen van een user
//    // Strip both ROLE_ADMIN and/or ROLE_USER from a user
//    @DeleteMapping(value = "/{username}/authorities/{authority}")
//    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
//        userService.removeAuthority(username, authority);
//        return ResponseEntity.noContent().build();
//    }

//    @GetMapping(value = "/exists/{username}")
//    public ResponseEntity<Object> doesUserExist(@PathVariable("username") String username) {
//        return ResponseEntity.ok().body("User " + "'" + username + "'" + " exists: " + userService.userExists(username));
//    }

    @GetMapping(value = "/search")
    public ResponseEntity<UserOutputDto> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

}