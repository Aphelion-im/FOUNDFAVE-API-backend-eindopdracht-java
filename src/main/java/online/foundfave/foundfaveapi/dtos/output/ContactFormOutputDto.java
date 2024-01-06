package online.foundfave.foundfaveapi.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactFormOutputDto {

    private String name;
    private String email;
    private String comments;

    // TODO: Nog Date toevoegen
    // TODO: Nog Time toevoegen
}
