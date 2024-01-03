package online.foundfave.foundfaveapi.dtos.output;

import online.foundfave.foundfaveapi.models.Authority;

import java.util.Set;

public class UserOutputDto {

    public String username;
//    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;

    // Laten staan?
    public Set<Authority> authorities;


}
