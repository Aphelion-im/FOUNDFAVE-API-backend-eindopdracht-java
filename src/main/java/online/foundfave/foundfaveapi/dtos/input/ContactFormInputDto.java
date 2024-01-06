package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "E-mail is required.")
    @Email(message = "E-mail address is not valid.")
    private String email;

    @NotNull(message = "Comments is required.")
    @Size(min=2, max=500, message="Message must be between 2 and 500 characters.")
    private String comments;
}
