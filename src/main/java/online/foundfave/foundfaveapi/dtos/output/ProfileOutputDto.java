package online.foundfave.foundfaveapi.dtos.output;

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
    public LocalDate dateOfBirth;
    public String profileImageUrl;
    public String fileName;
}
