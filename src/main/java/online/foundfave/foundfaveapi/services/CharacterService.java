package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.exceptions.CharacterNotFoundException;
import online.foundfave.foundfaveapi.exceptions.ContactFormNotFoundException;
import online.foundfave.foundfaveapi.models.Character;
import online.foundfave.foundfaveapi.models.ContactForm;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<CharacterOutputDto> getCharacters() {
        List<CharacterOutputDto> collection = new ArrayList<>();
        List<Character> list = characterRepository.findAll();
        for (Character character : list) {
            collection.add(transformCharacterToCharacterOutputDto(character));
        }
        return collection;
    }

    public CharacterOutputDto getCharacter(Long characterId) {
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character with id: " + characterId + " not found!"));
        return transformCharacterToCharacterOutputDto(character);
    }


    // Transformers
    // Character to CharacterOutputDto
    public static CharacterOutputDto transformCharacterToCharacterOutputDto(Character character) {
        var characterOutputDto = new CharacterOutputDto();
        characterOutputDto.characterId = character.getCharacterId();
        characterOutputDto.characterAliasName = character.getCharacterAliasName();
        characterOutputDto.characterRealName = character.getCharacterRealName();
        characterOutputDto.characterActorName = character.getCharacterActorName();
        characterOutputDto.characterTitle = character.getCharacterTitle();
        characterOutputDto.characterGender = character.getCharacterGender();
        characterOutputDto.characterSummary = character.getCharacterSummary();
        characterOutputDto.characterDescription = character.getCharacterDescription();
        characterOutputDto.characterImageUrl = character.getCharacterImageUrl();
        return characterOutputDto;
    }


}
