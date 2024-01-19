package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactFormInputDto {

    @NotNull(message = "Name is required and may not be null.")
    @Size(min=2, max=50, message="Name must be between 2 and 50 characters.")
    public String name;

    @NotBlank(message = "E-mail is required and may not be null.")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "This e-mail does not meet the expected requirements.")
    public String email;

    @NotNull(message = "Comments is required and may not be null.")
    @Size(min=2, max=255, message="Message must be between 2 and 255 characters.")
    public String comments;
}
