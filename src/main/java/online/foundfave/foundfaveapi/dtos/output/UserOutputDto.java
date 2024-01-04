package online.foundfave.foundfaveapi.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.models.Authority;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOutputDto {

    public String username;
    public Boolean enabled;
    public String email;

    // TODO: Laten staan?
    public Set<Authority> authorities;


}
