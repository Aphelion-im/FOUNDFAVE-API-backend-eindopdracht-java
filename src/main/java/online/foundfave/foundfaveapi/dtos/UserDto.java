package online.foundfave.foundfaveapi.dtos;

import lombok.Getter;
import lombok.Setter;
import online.foundfave.foundfaveapi.models.Authority;

import java.util.Set;

@Setter
@Getter
public class UserDto {

    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    public Set<Authority> authorities;

}
