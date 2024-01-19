package online.foundfave.foundfaveapi.dtos.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should only contain letters and numbers.")
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters.")
    public String username;

    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[!#@$%&/()=?*\\-+_.:;,{}^])[A-Za-z0-9!#@$%&/()=?*+-_.:;,{}]{8,20}", message = """
            Password needs to contain the following:\s
             1. Minimum of 1 lowercase letter.
             2. Minimum of 1 uppercase letter.
             3. Minimum of 1 number.
             4. Minimum of 1 symbol.
             5. It should be between 8 and 20 characters long.""")
    public String password;

    // For demo purposes, the apiKey will be filled with a random string, and therefore not a required field.
    public String apikey;

    @NotBlank(message = "E-mail is required.")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "This e-mail does not meet the expected requirements.")
    public String email;

    @JsonSerialize
    public Set<Authority> authorities;
}
