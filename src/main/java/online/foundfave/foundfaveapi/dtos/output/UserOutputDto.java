package online.foundfave.foundfaveapi.dtos.output;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.models.Authority;
import online.foundfave.foundfaveapi.models.Profile;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOutputDto {

    public String username;
    public Boolean enabled;
    public String email;
    public Profile profile;

    @JsonSerialize
    public Set<Authority> authorities;
}
