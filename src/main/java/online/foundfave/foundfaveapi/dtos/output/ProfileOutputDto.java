package online.foundfave.foundfaveapi.dtos.output;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ProfileOutputDto {

    public Long profileId;
    public String firstName;
    public String lastName;
    public Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "nl_NL", timezone = "Netherlands/Amsterdam")
    public LocalDate dateOfBirth;

    public String profileImageUrl;
    public String fileName;
}
