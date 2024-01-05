package online.foundfave.foundfaveapi.dtos.output;

public record AuthenticationResponse(String username, String authorities, String jwt ) {
}