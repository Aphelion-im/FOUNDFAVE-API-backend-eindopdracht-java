package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieInputDto {

    @NotNull(message = "Movie title is required and may not be null.")
    @Size(min = 2, max = 100, message = "Movie title requires between 2 and 100 characters.")
    public String movieTitle;

    @NotNull(message = "Movie summary is required and may not be null.")
    @Size(min = 2, max = 255, message = "Summary requires between 2 and 255 characters.")
    public String movieSummary;

    @NotNull(message = "You must enter 4 digits for movie year of release. E.g. 2019. Or fill in 0000 if you do not know.")
    @Pattern(regexp="\\d{4}", message = "You must enter 4 digits for movie year of release. E.g. 2019. Or fill in 0000 if you do not know.")
    public String movieYearOfRelease;

    @NotNull(message = "Movie image url is required and may not be null.")
    @Size(min = 2, max = 255, message = "Movie images url requires between 2 and 255 characters.")
    public String movieImageUrl;
}
