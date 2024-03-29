package online.foundfave.foundfaveapi.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.enums.Gender;
import online.foundfave.foundfaveapi.models.Movie;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacterOutputDto {

    public Long characterId;
    public String characterAliasName;
    public String characterRealName;
    public String characterActorName;
    public String characterTitle;
    public Gender characterGender;
    public String characterSummary;
    public String characterDescription;
    public String characterImageUrl;
    public String fileName;
    public List<Movie> moviesList;
}
