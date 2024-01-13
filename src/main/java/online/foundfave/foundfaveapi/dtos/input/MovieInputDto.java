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
public class MovieInputDto {

    @NotNull(message = "Movie title is required.")
    public String movieTitle;
}
