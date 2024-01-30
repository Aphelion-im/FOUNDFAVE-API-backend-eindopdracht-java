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

                //***** Public Endpoints ******//
                .requestMatchers(HttpMethod.GET, "/test").permitAll()
                .requestMatchers(HttpMethod.GET, "/info").permitAll()
                .requestMatchers(HttpMethod.GET, "/queries").permitAll()
                .requestMatchers(HttpMethod.GET, "/documentation/**").permitAll()

                //***** Authentication Endpoints *****//
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/authenticated").authenticated()

                //***** Characters Endpoints *****//
                .requestMatchers(HttpMethod.GET, "/characters").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/characters/{characterId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/characters/search/starting-with").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/characters/search/contains").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/characters/search/sorted-asc").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/characters/search/sorted-desc").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/characters/search/actor-name").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/characters/character/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/characters/associate/movie/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/characters").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/characters/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/characters/disassociate/movie/{characterId}").hasRole("ADMIN")

                //***** Contact Forms Endpoints *****//
                .requestMatchers(HttpMethod.GET, "/contactforms").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/contactforms/search/name").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/contactforms/search/email").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/contactforms/search/comments").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/contactforms/{contactFormId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/contactforms/post").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/contactforms").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/contactforms/{contactFormId}").hasAnyRole("ADMIN", "USER")

                //***** File Endpoints ******//
                .requestMatchers(HttpMethod.GET, "/download-profile-image/{fileName}").permitAll()
                .requestMatchers(HttpMethod.GET, "/download-character-image/{fileName}").permitAll()
                .requestMatchers(HttpMethod.GET, "/download-movie-image/{fileName}").permitAll()
                .requestMatchers(HttpMethod.POST, "/upload-profile-image/{profileId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/upload-character-image/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/upload-movie-image/{movieId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/delete-profile-image/{profileId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/delete-character-image/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/delete-movie-image/{movieId}").hasRole("ADMIN")

                //***** Movies Endpoints *****//
                .requestMatchers(HttpMethod.GET, "/movies").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/movies/{movieId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/movies/search/starting-with").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/movies/search/contains").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/movies/search/sorted-asc").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/movies/search/sorted-desc").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/movies/search/year").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/movies/movie/{movieId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/movies").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/movies/{movieId}").hasRole("ADMIN")

                //***** Profiles Endpoints *****//
                .requestMatchers(HttpMethod.GET, "/profiles").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/{profileId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/search/firstname").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/search/lastname").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/profile/{username}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/profiles/image/user/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/image/profile/{profileId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/profiles/profile/{profileId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/profiles").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/profiles/{profileId}").hasRole("ADMIN")

                //***** Users Endpoints *****//
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/users/active").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/favorites/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/search/email").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/search/username").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/exists/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/admin/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/user/{username}").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/users/assign/profile/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/detach/profile/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/add/character/{username}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities/{authority}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/remove/character/{username}").hasAnyRole("ADMIN", "USER")

                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}





