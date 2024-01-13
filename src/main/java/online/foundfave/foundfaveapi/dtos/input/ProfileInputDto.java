package online.foundfave.foundfaveapi.dtos.input;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileInputDto {

    @NotNull(message = "First name is required.")
    public String firstName;

    @NotNull(message = "Last name is required.")
    public String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is required.")
    public Gender gender;

    @NotNull(message = "Date is required.")
    @Past(message = "Date needs to be in the past.")
    @DateTimeFormat(pattern = "DD-MM-YYYY")
    public LocalDate dateOfBirth;

    public String profileImageUrl;
}
