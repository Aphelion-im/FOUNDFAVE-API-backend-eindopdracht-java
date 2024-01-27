package online.foundfave.foundfaveapi.configurations;

import online.foundfave.foundfaveapi.filters.JwtRequestFilter;
import online.foundfave.foundfaveapi.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // .requestMatchers("/**").permitAll()

                // TODO: Instellen juiste rollen
                // TODO: Testen met Admin rol en User rol
                // TODO: Online documentatie: combinatie endpoint + method name tabel
                // Postman: 74 (+ 1 method)
                // Config: 73

                //***** Public Endpoints ******//
                .requestMatchers(HttpMethod.GET, "/test").permitAll() // checkIfApiOnline (Public)
                .requestMatchers(HttpMethod.GET, "/info").permitAll() // showApiInfo (Public)
                .requestMatchers(HttpMethod.GET, "/queries").permitAll() // showAllAvailableQueries (Public)
                .requestMatchers(HttpMethod.GET, "/documentation/**").permitAll() // API documentation (Public)

                //***** Authentication Endpoints *****//
                .requestMatchers(HttpMethod.POST, "/login").permitAll() // Login (Public)
                .requestMatchers(HttpMethod.GET, "/authenticated").authenticated() // authenticated

                //***** Characters Endpoints *****//
                .requestMatchers(HttpMethod.GET, "/characters").hasAnyRole("ADMIN", "USER") // getAllCharacters
                .requestMatchers(HttpMethod.GET, "/characters/{characterId}").hasAnyRole("ADMIN", "USER") // getCharacterById
                .requestMatchers(HttpMethod.GET, "/characters/search/starting-with").hasAnyRole("ADMIN", "USER") // findCharactersByNameStartingWith
                .requestMatchers(HttpMethod.GET, "/characters/search/contains").hasAnyRole("ADMIN", "USER") // findCharactersByNameContains
                .requestMatchers(HttpMethod.GET, "/characters/search/sorted-asc").hasAnyRole("ADMIN", "USER") // findCharactersByNameSortedAsc
                .requestMatchers(HttpMethod.GET, "/characters/search/sorted-desc").hasAnyRole("ADMIN", "USER") // findCharactersByNameSortedDesc
                .requestMatchers(HttpMethod.GET, "/characters/search/actor-name").hasAnyRole("ADMIN", "USER") // findCharactersByActorNameContains
                .requestMatchers(HttpMethod.PUT, "/characters/character/{characterId}").hasRole("ADMIN") // updateCharacterById
                .requestMatchers(HttpMethod.PUT, "/characters/associate/movie/{characterId}").hasRole("ADMIN") // associateMovieAndCharacter
                .requestMatchers(HttpMethod.POST, "/characters").hasRole("ADMIN") // createCharacter
                .requestMatchers(HttpMethod.DELETE, "/characters/{characterId}").hasRole("ADMIN") // deleteCharacterById
                .requestMatchers(HttpMethod.DELETE, "/characters/disassociate/movie/{characterId}").hasRole("ADMIN") // disassociateMovieAndCharacter

                //***** Contact Forms Endpoints *****// TODO: Volgorde Controller en Service
                .requestMatchers(HttpMethod.GET, "/contactforms").hasRole("ADMIN") // getAllContactForms
                .requestMatchers(HttpMethod.GET, "/contactforms/search/name").hasAnyRole("ADMIN", "USER") // findContactFormsByNameContains
                .requestMatchers(HttpMethod.GET, "/contactforms/search/email").hasAnyRole("ADMIN", "USER") // findContactFormsByEmailContains
                .requestMatchers(HttpMethod.GET, "/contactforms/search/comments").hasAnyRole("ADMIN", "USER") // findContactFormsByCommentsContains
                .requestMatchers(HttpMethod.GET, "/contactforms/{contactFormId}").hasAnyRole("ADMIN", "USER") // getContactFormById
                .requestMatchers(HttpMethod.POST, "/contactforms/post").hasAnyRole("ADMIN", "USER") // createContactForm
                .requestMatchers(HttpMethod.DELETE, "/contactforms").hasAnyRole("ADMIN") // deleteAllContactForms
                .requestMatchers(HttpMethod.DELETE, "/contactforms/{contactFormId}").hasAnyRole("ADMIN", "USER") // deleteContactFormById

                //***** File Endpoints ******// TODO: Volgorde Controller en Service
                .requestMatchers(HttpMethod.GET, "/download-profile-image/{fileName}").permitAll() // downloadProfileImage
                .requestMatchers(HttpMethod.GET, "/download-character-image/{fileName}").permitAll() // downloadCharacterImage
                .requestMatchers(HttpMethod.GET, "/download-movie-image/{fileName}").permitAll() // downloadMovieImage
                .requestMatchers(HttpMethod.POST, "/upload-profile-image/{profileId}").hasAnyRole("ADMIN", "USER") // uploadProfileImageById
                .requestMatchers(HttpMethod.POST, "/upload-character-image/{characterId}").hasRole("ADMIN") // uploadCharacterImageById
                .requestMatchers(HttpMethod.POST, "/upload-movie-image/{movieId}").hasRole("ADMIN") // uploadMovieImageById
                .requestMatchers(HttpMethod.DELETE, "/delete-profile-image/{profileId}").hasAnyRole("ADMIN", "USER") // deleteProfileImageById
                .requestMatchers(HttpMethod.DELETE, "/delete-character-image/{characterId}").hasRole("ADMIN") // deleteCharacterImageById
                .requestMatchers(HttpMethod.DELETE, "/delete-movie-image/{movieId}").hasRole("ADMIN") // deleteMovieImageById

                //***** Movies Endpoints *****// TODO: Volgorde Controller en Service
                .requestMatchers(HttpMethod.GET, "/movies").hasAnyRole("ADMIN", "USER") // getAllMovies
                .requestMatchers(HttpMethod.GET, "/movies/{movieId}").hasAnyRole("ADMIN", "USER") // getMovieById
                .requestMatchers(HttpMethod.GET, "/movies/search/starting-with").hasAnyRole("ADMIN", "USER") // findMoviesByTitleStartingWith
                .requestMatchers(HttpMethod.GET, "/movies/search/contains").hasAnyRole("ADMIN", "USER") // findMoviesByTitleContains
                .requestMatchers(HttpMethod.GET, "/movies/search/sorted-asc").hasAnyRole("ADMIN", "USER") // findMoviesByTitleSortedAsc
                .requestMatchers(HttpMethod.GET, "/movies/search/sorted-desc").hasAnyRole("ADMIN", "USER") // findMoviesByTitleSortedDesc
                .requestMatchers(HttpMethod.GET, "/movies/search/year").hasAnyRole("ADMIN", "USER") // findMovieByYearOfRelease
                .requestMatchers(HttpMethod.PUT, "/movies/movie/{movieId}").hasRole("ADMIN") // updateMovieById
                .requestMatchers(HttpMethod.POST, "/movies").hasRole("ADMIN") // createMovie
                .requestMatchers(HttpMethod.DELETE, "/movies/{movieId}").hasRole("ADMIN") // deleteMovieById

                //***** Profiles Endpoints *****// TODO: Volgorde Controller en Service
                .requestMatchers(HttpMethod.GET, "/profiles").hasRole("ADMIN") // getAllProfiles


                .requestMatchers(HttpMethod.GET, "/profiles/{profileId}").hasAnyRole("ADMIN", "USER") // getProfileById


                .requestMatchers(HttpMethod.GET, "/profiles/search/firstname").hasRole("ADMIN") // findProfileByFirstnameContains
                .requestMatchers(HttpMethod.GET, "/profiles/search/lastname").hasRole("ADMIN") // findProfileByLastNameContains
                .requestMatchers(HttpMethod.GET, "/profiles/profile/{username}").hasRole("ADMIN") // getProfileByUsername
                .requestMatchers(HttpMethod.GET, "profiles/image/user/{username}").hasRole("ADMIN") // getProfileImageByUsername
                .requestMatchers(HttpMethod.GET, "profiles/image/profile/{profileId}").hasRole("ADMIN") // getProfileImageByProfileId
                .requestMatchers(HttpMethod.PUT, "/profiles/profile/{profileId}").hasAnyRole("ADMIN", "USER") // updateProfileById
                .requestMatchers(HttpMethod.POST, "/profiles").hasAnyRole("ADMIN", "USER") // createProfile
                .requestMatchers(HttpMethod.DELETE, "/profiles/{profileId}").hasRole("ADMIN") // deleteProfile

                //***** Users Endpoints *****// TODO: Volgorde Controller en Service
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN") // getAllUsers
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN", "USER") // getUserByUsername
                .requestMatchers(HttpMethod.GET, "/users/active").hasRole("ADMIN") // getActiveUsers
                .requestMatchers(HttpMethod.GET, "/users/favorites/{username}").hasRole("ADMIN") // getAllFavoritesFromUser
                .requestMatchers(HttpMethod.GET, "/users/search/email").hasRole("ADMIN") // findUserByEmail (Specific) & findUserByEmailContains (Returns a list)
                .requestMatchers(HttpMethod.GET, "/users/search/username").hasRole("ADMIN") // findUserByUsernameContains
                .requestMatchers(HttpMethod.GET, "/users/exists/{username}").hasRole("ADMIN") // doesUserExist
                .requestMatchers(HttpMethod.GET, "/users/{username}/authorities").hasRole("ADMIN") // getUserAuthorities
                .requestMatchers(HttpMethod.PUT, "/users/admin/{username}").hasRole("ADMIN") // updateUserPasswordAdmin (Admin only)
                .requestMatchers(HttpMethod.PUT, "/users/user/{username}").hasAnyRole("ADMIN", "USER") // updateUser (User only)
                .requestMatchers(HttpMethod.PUT, "/users/assign/profile/{username}").hasRole("ADMIN") // assignProfileToUser
                .requestMatchers(HttpMethod.PUT, "/users/detach/profile/{username}").hasRole("ADMIN") // detachProfileFromToUser
                .requestMatchers(HttpMethod.PUT, "/users/add/character/{username}").hasRole("ADMIN") // addFavoriteCharacterToUser
                .requestMatchers(HttpMethod.POST, "/users").permitAll() // createUser
                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasRole("ADMIN") // addUserAuthority
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN") // deleteUser
                .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities/{authority}").hasRole("ADMIN") // removeAuthority
                .requestMatchers(HttpMethod.DELETE, "/users/remove/character/{username}").hasRole("ADMIN") // removeFavoriteCharacterFromUser

                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}





