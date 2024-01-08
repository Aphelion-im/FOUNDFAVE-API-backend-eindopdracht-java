package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }
}
