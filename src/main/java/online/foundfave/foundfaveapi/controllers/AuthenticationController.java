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

    // TODO: Controleren of de lijst wel klopt
    // TODO: Alle endpoints invoeren
    @GetMapping("/info")
    public ResponseEntity<String> showApiInfo() {
        String info = """
                FOUNDFAVE API Endpoints
                                
                                
                ***** Public *****
                localhost:8080/ (GET, Public)
                localhost:8080/test (GET, Public)
                localhost:8080/queries (GET, Public)
                http://localhost:8080/documentation/index.html (GET, Public)
                                
                ***** Authentication *****
                localhost:8080/login (POST, Public)
                localhost:8080/authenticated (GET, Admin, User)
                                
                ***** Characters *****
                                
                                
                ***** Contact Forms *****
                                
                                
                ***** Files *****
                                
                                
                ***** Movies *****
                                
                                
                                
                ***** Profiles *****
                                
                    
                ***** Users *****
                            
                                
                                
                                
                                
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
