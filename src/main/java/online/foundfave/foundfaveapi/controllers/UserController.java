package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.UserDto;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.UserOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.RecordNotFoundException;
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