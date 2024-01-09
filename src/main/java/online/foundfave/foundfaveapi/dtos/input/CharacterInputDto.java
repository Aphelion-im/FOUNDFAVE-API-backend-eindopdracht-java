package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacterInputDto {

    @NotNull(message = "Character alias name is required.")
    public String characterAliasName;

    @NotNull(message = "Character real name is required.")
    public String characterRealName;

    @NotNull(message = "Character actor name is required.")
    public String characterActorName;

    @NotNull(message = "Character title is required.")
    public String characterTitle;

    @NotNull(message = "Character gender is required.")
    public String characterGender;

    @NotNull(message = "Character summary is required.")
    public String characterSummary;

    @NotNull(message = "Character description is required.")
    public String characterDescription;

    @NotNull(message = "Character image url is required.")
    public String characterImageUrl;
}
