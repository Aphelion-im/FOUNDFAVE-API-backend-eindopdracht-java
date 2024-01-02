package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import online.foundfave.foundfaveapi.models.Authority;

import java.util.Set;

@Data
public class UserInputDto {

    @NotBlank(message = "Username is mandatory.")
    @Size(min=2, max=30, message="Username must be at least 2 characters and have a maximum of 30 characters.")
    public String username;

    @NotBlank(message = "Password is mandatory.")
    @Size(min=6, max=30, message="Password must be at least 6 characters and have a maximum of 30 characters.")
    public String password;

    public Boolean enabled;

    public String apikey;

    @NotBlank(message = "E-mail is mandatory.")
    @Email
    public String email;


    // Laten staan?
    public Set<Authority> authorities;



}
