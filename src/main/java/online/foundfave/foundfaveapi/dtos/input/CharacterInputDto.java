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
    private String characterAliasName;

    @NotNull(message = "Character real name is required.")
    private String characterRealName;

    @NotNull(message = "Character actor name is required.")
    private String characterActorName;

    @NotNull(message = "Character title is required.")
    private String characterTitle;

    @NotNull(message = "Character gender is required.")
    private String characterGender;

    @NotNull(message = "Character summary is required.")
    private String characterSummary;

    @NotNull(message = "Character description is required.")
    private String characterDescription;

    @NotNull(message = "Character image url is required.")
    private String characterImageUrl;
}
