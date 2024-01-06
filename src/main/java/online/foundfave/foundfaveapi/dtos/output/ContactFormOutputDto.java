package online.foundfave.foundfaveapi.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactFormOutputDto {

    public Long id;
    public String name;
    public String email;
    public String comments;
    public Date submissionDate;
    public Time submissionTime;
}
