package online.foundfave.foundfaveapi.dtos.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.enums.Gender;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileInputDto {

    @NotNull(message = "First name is required and may not be null.")
    @Size(min = 1, max = 50, message = "First name field requires between between 1 and 50 characters.")
    public String firstName;

    @NotNull(message = "Last name field is required and may not be null.")
    @Size(min = 1, max = 50, message = "First name field requires between between 1 and 50 characters.")
    public String lastName;

    @NotNull(message = "Gender is required and may not be null.")
    @Enumerated(EnumType.STRING)
    public Gender gender;

    @NotNull(message = "Date is required and may not be null.")
    @Past(message = "Date needs to be in the past.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "nl_NL", timezone = "Netherlands/Amsterdam")
    public LocalDate dateOfBirth;

    // TODO: Nog niet zeker of deze er in moeten en of er validatie op moet
    public String profileImageUrl;
    public String fileName;
}
