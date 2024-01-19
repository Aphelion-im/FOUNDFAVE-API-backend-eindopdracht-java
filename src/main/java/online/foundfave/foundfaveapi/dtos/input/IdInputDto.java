package online.foundfave.foundfaveapi.dtos.input;

import jakarta.validation.constraints.NotNull;

public class IdInputDto {

    @NotNull(message = "Id is required and may not be null or empty.")
    public Long id;
}





