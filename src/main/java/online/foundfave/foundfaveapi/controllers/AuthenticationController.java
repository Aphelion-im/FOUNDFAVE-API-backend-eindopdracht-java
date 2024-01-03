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

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        String info = """
                FOUNDFAVE API Endpoints
                                
                ***** Info *****
                localhost:8080/info

                ***** Authentication *****
                localhost:8080/login
                localhost:8080/authenticated
                    
                ***** Users *****
                localhost:8080/users
                """;

        return ResponseEntity.ok().body(info);
    }


}
