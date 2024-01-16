package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieInputDto {

    @NotNull(message = "Movie title is required.")
    public String movieTitle;

    @NotNull(message = "Movie summary is required.")
    public String movieSummary;

    @NotNull(message = "You must enter 4 digits for movie year of release. E.g. 2019. Or fill in 0000 if you do not know.")
    @Positive
    public Integer movieYearOfRelease;

    @NotNull(message = "Movie image url is required.")
    public String movieImageUrl;
}
