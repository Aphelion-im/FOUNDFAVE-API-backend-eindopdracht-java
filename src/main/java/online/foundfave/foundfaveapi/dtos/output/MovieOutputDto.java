package online.foundfave.foundfaveapi.dtos.output;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieOutputDto {

    public Long movieId;
    public String movieTitle;
    public String movieSummary;
    public int movieYearOfRelease;
    public String movieImageUrl;
}
