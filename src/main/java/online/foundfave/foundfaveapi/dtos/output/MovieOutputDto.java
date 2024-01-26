package online.foundfave.foundfaveapi.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.models.Character;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieOutputDto {

    public Long movieId;
    public String movieTitle;
    public String movieSummary;
    public String movieYearOfRelease;
    public String movieImageUrl;
    public String fileName;
    public List<Character> charactersList;
}
