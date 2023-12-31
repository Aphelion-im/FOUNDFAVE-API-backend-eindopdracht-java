package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.models.Authority;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInputDto {

    @NotBlank(message = "Username is required.")
    @Size(min = 2, max = 30, message = "Username must be at least 2 characters and have a maximum of 30 characters.")
    public String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 30, message = "Password must be at least 6 characters and have a maximum of 30 characters.")
    public String password;

    // For demo purposes, the apiKey will be filled with a random string, and therefore not a required field.
    public String apikey;

    @NotBlank(message = "E-mail is required.")
    @Email(message = "E-mail address is not valid.")
    public String email;

    public Set<Authority> authorities;
}
