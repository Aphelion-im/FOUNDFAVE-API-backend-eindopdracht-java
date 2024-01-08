package online.foundfave.foundfaveapi.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacterOutputDto {

    private String characterAliasName;
    private String characterRealName;
    private String characterActorName;
    private String characterTitle;
    private String characterGender;
    private String characterSummary;
    private String characterDescription;
    private String characterImageUrl;
}
