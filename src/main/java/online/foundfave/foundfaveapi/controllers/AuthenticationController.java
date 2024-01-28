package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.input.AuthenticationRequest;
import online.foundfave.foundfaveapi.dtos.output.AuthenticationResponse;
import online.foundfave.foundfaveapi.services.CustomUserDetailsService;
import online.foundfave.foundfaveapi.utilities.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtUtl;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtl) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtl = jwtUtl;
    }

    @GetMapping("/test")
    public String checkIfApiOnline() {
        return "The FOUNDFAVE API is online.";
    }

    @GetMapping("/info")
    public ResponseEntity<String> showApiInfo() {
        String info = """
               FOUNDFAVE API Endpoints Quick Reference
                                
               ***** Public Endpoints ******
               GET, "/test", Postman: checkIfApiOnline (Public)
               GET, "/info", Postman: showApiInfo (Public)
               GET, "/queries", Postman: showAllAvailableQueries (Public)
               GET, "/documentation/index.html", Postman: API documentation (Public)
                
               ***** Authentication Endpoints *****
               POST, "/login", Postman: Login (Public)
               GET, "/authenticated", Postman: authenticated (Authenticated)
                
               ***** Characters Endpoints *****
               GET, "/characters", Postman: getAllCharacters (Admin, User)
               GET, "/characters/{characterId}", Postman: getCharacterById (Admin, User)
               GET, "/characters/search/starting-with", Postman: findCharactersByNameStartingWith (Admin, User)
               GET, "/characters/search/contains", Postman: findCharactersByNameContains (Admin, User)
               GET, "/characters/search/sorted-asc", Postman: findCharactersByNameSortedAsc (Admin, User)
               GET, "/characters/search/sorted-desc", Postman: findCharactersByNameSortedDesc (Admin, User)
               GET, "/characters/search/actor-name", Postman: findCharactersByActorNameContains (Admin, User)
               PUT, "/characters/character/{characterId}", Postman: updateCharacterById (Admin)
               PUT, "/characters/associate/movie/{characterId}", Postman: associateMovieAndCharacter (Admin)
               POST, "/characters", Postman: createCharacter (Admin)
               DELETE, "/characters/{characterId}", Postman: deleteCharacterById (Admin)
               DELETE, "/characters/disassociate/movie/{characterId}", Postman: disassociateMovieAndCharacter (Admin)
                
               ***** Contact Forms Endpoints *****
               GET, "/contactforms", Postman: getAllContactForms (Admin)
               GET, "/contactforms/search/name", Postman: findContactFormsByNameContains (Admin, User)
               GET, "/contactforms/search/email", Postman: findContactFormsByEmailContains (Admin, User)
               GET, "/contactforms/search/comments", Postman: findContactFormsByCommentsContains (Admin, User)
               GET, "/contactforms/{contactFormId}", Postman: getContactFormById (Admin, User)
               POST, "/contactforms/post", Postman: createContactForm (Admin, User)
               DELETE, "/contactforms", Postman: deleteAllContactForms (Admin)
               DELETE, "/contactforms/{contactFormId}", Postman: deleteContactFormById (Admin, User)
               
               ***** File Endpoints ******
               GET, "/download-profile-image/{fileName}", Postman: downloadProfileImage (Public)
               GET, "/download-character-image/{fileName}", Postman: downloadCharacterImage (Public)
               GET, "/download-movie-image/{fileName}", Postman: downloadMovieImage (Public)
               POST, "/upload-profile-image/{profileId}", Postman: uploadProfileImageById (Admin, User)
               POST, "/upload-character-image/{characterId}", Postman: uploadCharacterImageById (Admin)
               POST, "/upload-movie-image/{movieId}", Postman: uploadMovieImageById (Admin)
               DELETE, "/delete-profile-image/{profileId}", Postman: deleteProfileImageById (Admin, User)
               DELETE, "/delete-character-image/{characterId}", Postman: deleteCharacterImageById (Admin)
               DELETE, "/delete-movie-image/{movieId}", Postman: deleteMovieImageById (Admin)
                
               ***** Movies Endpoints *****
               GET, "/movies", Postman: getAllMovies (Admin, User)
               GET, "/movies/{movieId}", Postman: getMovieById (Admin, User)
               GET, "/movies/search/starting-with", Postman: findMoviesByTitleStartingWith (Admin, User)
               GET, "/movies/search/contains", Postman: findMoviesByTitleContains (Admin, User)
               GET, "/movies/search/sorted-asc", Postman: findMoviesByTitleSortedAsc (Admin, User)
               GET, "/movies/search/sorted-desc", Postman: findMoviesByTitleSortedDesc (Admin, User)
               GET, "/movies/search/year", Postman: findMovieByYearOfRelease (Admin, User)
               PUT, "/movies/movie/{movieId}", Postman: updateMovieById (Admin)
               POST, "/movies", Postman: createMovie (Admin)
               DELETE, "/movies/{movieId}", Postman: deleteMovieById (Admin)
                
               ***** Profiles Endpoints *****
               GET, "/profiles", Postman: getAllProfiles (Admin)
               GET, "/profiles/{profileId}", Postman: getProfileById (Admin)
               GET, "/profiles/search/firstname", Postman: findProfileByFirstnameContains (Admin)
               GET, "/profiles/search/lastname", Postman: findProfileByLastNameContains (Admin)
               GET, "/profiles/profile/{username}", Postman: getProfileByUsername (Admin, User)
               GET, "/profiles/image/user/{username}", Postman: getProfileImageByUsername (Admin)
               GET, "/profiles/image/profile/{profileId}", Postman: getProfileImageByProfileId (Admin)
               PUT, "/profiles/profile/{profileId}", Postman: updateProfileById (Admin, User)
               POST, "/profiles", Postman: createProfile (Admin, User)
               DELETE, "/profiles/{profileId}", Postman: deleteProfileById (Admin)
                
               ***** Users Endpoints *****
               GET, "/users", Postman: getAllUsers (Admin)
               GET, "/users/{username}", Postman: getUserByUsername (Admin, User)
               GET, "/users/active", Postman: getActiveUsers (Admin)
               GET, "/users/favorites/{username}", Postman: getAllFavoritesFromUser (Admin)
               GET, "/users/search/email", Postman: findUserByEmail (Specific) & findUserByEmailContains (Returns a list) (Admin)
               GET, "/users/search/username", Postman: findUserByUsernameContains (Admin)
               GET, "/users/exists/{username}", Postman: doesUserExist (Admin)
               GET, "/users/{username}/authorities", Postman: getUserAuthorities (Admin)
               PUT, "/users/admin/{username}", Postman: updateUserPasswordAdmin (Admin only) (Admin)
               PUT, "/users/user/{username}", Postman: updateUserDetails (User only) (USER)
               PUT, "/users/assign/profile/{username}", Postman: assignProfileToUser (Admin)
               PUT, "/users/detach/profile/{username}", Postman: detachProfileFromToUser (Admin)
               PUT, "/users/add/character/{username}", Postman: addFavoriteCharacterToUser (Admin, User)
               POST, "/users", Postman: createUser (Public)
               POST, "/users/{username}/authorities", Postman: addUserAuthority (Admin)
               DELETE, "/users/{username}", Postman: deleteUser (Admin)
               DELETE, "/users/{username}/authorities/{authority}", Postman: removeAuthority (Admin)
               DELETE, "/users/remove/character/{username}", Postman: removeFavoriteCharacterFromUser (Admin, User)
                """;
        return ResponseEntity.ok().body(info);
    }

    @GetMapping("/queries")
    public ResponseEntity<String> showAllAvailableQueries() {
        String info = """
                FOUNDFAVE API Queries
                                
                The following characters and movies are currently in the FOUNDFAVE API database:
                                
                ***** Characters *****
                Ant-Man
                Black Panther
                Black Widow
                Captain America
                Captain Marvel
                Doctor Strange
                Hawkeye
                Hulk
                Iron Man
                Nick Fury
                Scarlet Witch
                Spider-Man
                Thor
                Vision
                Winter Soldier

                ***** Movies *****
                Ant-Man
                Ant-Man and the Wasp
                Ant-Man and the Wasp: Quantumania
                Avengers: Age of Ultron
                Avengers: Endgame
                Avengers: Infinity War
                Black Panther
                Black Panther: Wakanda Forever
                Black Widow
                Captain America: Brave New World
                Captain America: Civil War
                Captain America: The first avenger
                Captain America: The Winter Soldier
                Captain Marvel
                Doctor Strange
                Doctor Strange in the Multiverse of Madness
                Iron Man
                Iron Man 2
                Iron Man 3
                Spider-Man 4
                Spider-Man: Far From Home
                Spider-Man: Homecoming
                Spider-Man: No Way Home
                The Avengers
                The Incredible Hulk
                The Marvels
                Thor
                Thor: Love and Thunder
                Thor: Ragnarok
                Thor: The Dark World
                Thunderbolts
                """;

        return ResponseEntity.ok().body(info);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException bce) {
            throw new BadCredentialsException("Username: " + "'" + username + "'" + " not found!", bce);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);
        final String jwt = jwtUtl.generateToken(userDetails);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).body(new AuthenticationResponse(username, userDetails.getAuthorities().toString(), jwt));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<Object> authenticated(Principal principal) {
        return ResponseEntity.ok().body(principal);
    }
}
