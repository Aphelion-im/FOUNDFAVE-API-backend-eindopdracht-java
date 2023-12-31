package online.foundfave.foundfaveapi.config;

import online.foundfave.foundfaveapi.filter.JwtRequestFilter;
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
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
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

                // TODO: Beter optimaliseren en verdelen
                /* Users */
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/admin/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/user/{username}").hasRole("USER")
                .requestMatchers("/authenticated").authenticated()


                /* ContactForm */
                .requestMatchers(HttpMethod.GET, "/contactforms").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/contactforms/{contactFormId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/contactforms/{contactFormId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/contactforms/**").hasRole("ADMIN") // TODO: Ook User

                /* Characters */
                .requestMatchers(HttpMethod.GET, "/characters").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/characters/{characterId}").hasRole("ADMIN")



                /* Public */
                .requestMatchers("/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/info").permitAll()
                .requestMatchers(HttpMethod.GET, "/test").permitAll()
                .requestMatchers(HttpMethod.GET, "/queries").permitAll()


                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}





