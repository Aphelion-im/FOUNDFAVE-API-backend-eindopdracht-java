package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.AuthenticationRequest;
import online.foundfave.foundfaveapi.dtos.AuthenticationResponse;
import online.foundfave.foundfaveapi.services.CustomUserDetailsService;
import online.foundfave.foundfaveapi.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> logIn(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException bce) {
            throw new Exception("Incorrect username or password", bce);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);
        final String jwt = jwtUtl.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // Check to see if the FOUNDFAVE API is up and running
    @GetMapping("/test")
    public String test() {
        return "The FOUNDFAVE API can be reached.";
    }

    // Show all available FOUNDFAVE API endpoints
    @GetMapping("/info")
    public ResponseEntity<String> info() {
        String info = """
                FOUNDFAVE API Endpoints
                                
                ***** Info *****
                localhost:8080/ (GET, Public)
                localhost:8080/test (GET, Public)
                localhost:8080/queries (GET, Public)

                ***** Authentication *****
                localhost:8080/login (POST, Public)
                localhost:8080/authenticated (GET, ?)
                    
                ***** Users *****
                localhost:8080/users (GET, Admin)
                localhost:8080/users/{username} (GET, Admin)
                localhost:8080/users (POST, Public)
                localhost:8080/users/{username} (POST, ?)
                localhost:8080/users/{username} (DELETE, ?)
                localhost:8080/users/{username}/authorities (GET, ?)
                localhost:8080/users/{username}/authorities (POST, ?)
                localhost:8080/users/{username}/authorities/{authority} (DELETE, ?)
                localhost:8080/users/exists/{username} (GET, ?)
                localhost:8080/users/search?email={email} (GET, Admin)
                
                
                
                
                """;

        return ResponseEntity.ok().body(info);
    }

    // Show all available FOUNDFAVE API characters & movies
    @GetMapping("/queries")
    public ResponseEntity<String> queries() {
        String info = """
                FOUNDFAVE API Queries
                
                The following characters and movies are currently in the database:
                                
                ***** Characters *****
              

                ***** Movies *****
          
                
                
                
                
                """;

        return ResponseEntity.ok().body(info);
    }


}
