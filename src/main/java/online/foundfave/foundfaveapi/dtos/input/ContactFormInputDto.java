package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactFormInputDto {

    @NotNull(message = "Name is required.")
    private String name;

    @NotNull(message = "Email is required.")
    @Email(message = "E-mail address is not valid.")
    private String email;

    @NotNull(message = "Comments is required.")
    private String comments;
}
