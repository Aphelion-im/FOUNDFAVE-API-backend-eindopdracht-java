package online.foundfave.foundfaveapi.dtos.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactFormOutputDto {

    public Long contactFormId;
    public String name;
    public String email;
    public String comments;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    public LocalDateTime timeStamp;

}
