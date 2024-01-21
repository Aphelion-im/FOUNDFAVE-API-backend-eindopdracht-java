package online.foundfave.foundfaveapi.configuration;

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

                /* Public */
                .requestMatchers(HttpMethod.GET, "/info").permitAll()
                .requestMatchers(HttpMethod.GET, "/test").permitAll()
                .requestMatchers(HttpMethod.GET, "/queries").permitAll()
                .requestMatchers(HttpMethod.GET, "/documentation/**").permitAll()

                /* Authentication */
                .requestMatchers("/login").permitAll()
                .requestMatchers("/authenticated").authenticated()

                /* Characters */
                .requestMatchers(HttpMethod.GET, "/characters").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/characters/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/characters/search/starting-with").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/characters/search/contains").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/characters/search/sorted-asc").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/characters/search/sorted-desc").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/characters/search/actor-name").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/characters/character/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/characters/add/movie/{characterId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/characters").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/characters/{characterId}").hasRole("ADMIN")

                /* ContactForms */
                .requestMatchers(HttpMethod.GET, "/contactforms").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/contactforms/search/name").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/contactforms/search/email").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/contactforms/search/comments").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/contactforms/{contactFormId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/contactforms/post").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/contactforms").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/contactforms/{contactFormId}").hasRole("ADMIN")

                /* Movies */
                .requestMatchers(HttpMethod.GET, "/movies").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/movies/{movieId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/movies/search/starting-with").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/movies/search/contains").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/movies/search/sorted-asc").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/movies/search/sorted-desc").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/movies/search/year").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/movies/movie/{movieId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/movies").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/movies/{movieId}").hasRole("ADMIN")

                /* Profiles */
                .requestMatchers(HttpMethod.GET, "/profiles").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/{profileId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/search/firstname").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/search/lastname").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/profiles/profile/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/profiles/profile/{profileId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/profiles").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/profiles/{profileId}").hasRole("ADMIN")

                /* Users */
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/active").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/favorites/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/search/email").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/search/username").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/exists/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/admin/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/user/{username}").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/users/assign/profile/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/detach/profile/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/add/character/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities/{authority}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/delete/character/{username}").hasRole("ADMIN")


                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}





