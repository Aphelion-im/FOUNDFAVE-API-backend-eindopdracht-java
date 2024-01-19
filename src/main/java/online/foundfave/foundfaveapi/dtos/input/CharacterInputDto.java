package online.foundfave.foundfaveapi.dtos.input;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.enums.Gender;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacterInputDto {

    @NotNull(message = "Character alias name is required and may not be null.")
    @Size(min=1, max=50, message="Character alias requires between between 1 and 50 characters.")
    public String characterAliasName;

    @NotNull(message = "Character real name is required and may not be null.")
    @Size(min=1, max=50, message="Character actor requires between between 1 and 50 characters.")
    public String characterRealName;

    @NotNull(message = "Character actor name is required and may not be null.")
    @Size(min=1, max=50, message="Character actor requires between between 1 and 50 characters.")
    public String characterActorName;

    @NotNull(message = "Character title is required and may not be null.")
    @Size(min=1, max=150, message="Character title requires between 1 and 150 characters.")
    public String characterTitle;

    @NotNull(message = "Character gender is required and may not be null. The following gender options are available: Male, Female, X.")
    @Enumerated(EnumType.STRING)
    public Gender characterGender;

    @NotNull(message = "Character summary is required and may not be null.")
    @Size(min=1, max=255, message="Character summary requires between between 1 and 150 characters.")
    public String characterSummary;

    @NotNull(message = "Character description is required and may not be null.")
    @Size(min=1, max=255, message="Character description requires between 1 and 255 characters.")
    public String characterDescription;

    @NotNull(message = "Character image url is required and may not be null.")
    @NotBlank(message = "Character image url is required and may not be blank.")
    public String characterImageUrl;
}
