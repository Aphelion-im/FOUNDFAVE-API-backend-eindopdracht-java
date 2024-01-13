package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(min=4, max=4, message="You must enter 4 digits.")
    public int movieYearOfRelease;

    @NotNull(message = "Movie image url is required.")
    public String movieImageUrl;
}
