package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.CharacterInputDto;
import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.exceptions.*;
import online.foundfave.foundfaveapi.models.Character;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import online.foundfave.foundfaveapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;

    public CharacterService(CharacterRepository characterRepository, MovieRepository movieRepository) {
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
    }

    public List<CharacterOutputDto> getAllCharacters() {
        List<CharacterOutputDto> characterOutputDtoList = new ArrayList<>();
        List<Character> list = characterRepository.findAll();
        for (Character character : list) {
            characterOutputDtoList.add(transformCharacterToCharacterOutputDto(character));
        }
        return characterOutputDtoList;
    }

    public CharacterOutputDto getCharacterById(Long characterId) {
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character with id: " + characterId + " not found!"));
        return transformCharacterToCharacterOutputDto(character);
    }

    public List<CharacterOutputDto> findCharactersByNameStartingWith(String name) {
        List<CharacterOutputDto> characterOutputDtoList = new ArrayList<>();
        List<Character> list = characterRepository.findByCharacterAliasNameStartingWithIgnoreCase(name);
        for (Character character : list) {
            characterOutputDtoList.add(transformCharacterToCharacterOutputDto(character));
        }
        if (characterOutputDtoList.isEmpty()) {
            throw new CharacterNotFoundException("0 results. No characters were found!");
        }
        return characterOutputDtoList;
    }

    public List<CharacterOutputDto> findCharactersByNameContains(String name) {
        List<CharacterOutputDto> characterOutputDtoList = new ArrayList<>();
        List<Character> list = characterRepository.findByCharacterAliasNameContainsIgnoreCase(name);
        for (Character character : list) {
            characterOutputDtoList.add(transformCharacterToCharacterOutputDto(character));
        }
        if (characterOutputDtoList.isEmpty()) {
            throw new CharacterNotFoundException("0 results. No characters were found!");
        }
        return characterOutputDtoList;
    }

    public List<CharacterOutputDto> findCharactersByNameSortedAsc(String name) {
        List<CharacterOutputDto> characterOutputDtoList = new ArrayList<>();
        List<Character> list = characterRepository.findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameAsc(name);
        for (Character character : list) {
            characterOutputDtoList.add(transformCharacterToCharacterOutputDto(character));
        }
        if (characterOutputDtoList.isEmpty()) {
            throw new CharacterNotFoundException("0 results. No characters were found!");
        }
        return characterOutputDtoList;
    }

    public List<CharacterOutputDto> findCharactersByNameSortedDesc(String name) {
        List<CharacterOutputDto> characterOutputDtoList = new ArrayList<>();
        List<Character> list = characterRepository.findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameDesc(name);
        for (Character character : list) {
            characterOutputDtoList.add(transformCharacterToCharacterOutputDto(character));
        }
        if (characterOutputDtoList.isEmpty()) {
            throw new CharacterNotFoundException("0 results. No characters were found!");
        }
        return characterOutputDtoList;
    }

    public List<CharacterOutputDto> findCharactersByActorNameContains(String name) {
        List<CharacterOutputDto> characterOutputDtoList = new ArrayList<>();
        List<Character> list = characterRepository.findByCharacterActorNameContainsIgnoreCase(name);
        for (Character character : list) {
            characterOutputDtoList.add(transformCharacterToCharacterOutputDto(character));
        }
        if (characterOutputDtoList.isEmpty()) {
            throw new CharacterNotFoundException("0 results. No characters were found!");
        }
        return characterOutputDtoList;
    }

    public String updateCharacterById(Long characterId, CharacterInputDto characterInputDto) {
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        if (characterInputDto.characterAliasName != null) {
            character.setCharacterAliasName(characterInputDto.getCharacterAliasName());
        }
        if (characterInputDto.characterRealName != null) {
            character.setCharacterRealName(characterInputDto.getCharacterRealName());
        }
        if (characterInputDto.characterActorName != null) {
            character.setCharacterActorName(characterInputDto.getCharacterActorName());
        }
        if (characterInputDto.characterTitle != null) {
            character.setCharacterTitle(characterInputDto.getCharacterTitle());
        }
        if (characterInputDto.characterGender != null) {
            character.setCharacterGender(characterInputDto.getCharacterGender());
        }
        if (characterInputDto.characterSummary != null) {
            character.setCharacterSummary(characterInputDto.getCharacterSummary());
        }
        if (characterInputDto.characterDescription != null) {
            character.setCharacterDescription(characterInputDto.getCharacterDescription());
        }
        characterRepository.save(character);
        return "Character with id: " + character.getCharacterId() + " updated successfully!";
    }

    public CharacterOutputDto associateMovieAndCharacter(Long characterId, Long movieId) {
        var optionalCharacter = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        var optionalMovie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId + "!"));
        if (optionalCharacter.getMoviesList().contains(optionalMovie)) {
            throw new BadRequestException("This movie is already associated with this character!");
        } else {
            optionalCharacter.getMoviesList().add(optionalMovie);
            var updatedOptionalCharacter = characterRepository.save(optionalCharacter);
            return transformCharacterToCharacterOutputDto(updatedOptionalCharacter);
        }
//        optionalCharacter.getMoviesList().add(optionalMovie);
//        var updatedOptionalCharacter = characterRepository.save(optionalCharacter);
//        return transformCharacterToCharacterOutputDto(updatedOptionalCharacter);
    }

    public Character createCharacter(CharacterInputDto characterInputDto) {
        Optional<Character> optionalCharacter = characterRepository.findByCharacterAliasNameIgnoreCase(characterInputDto.characterAliasName);
        if (optionalCharacter.isPresent()) {
            throw new CharacterAlreadyExistsException("Character: " + "'" + characterInputDto.characterAliasName + "'" + " already exists!");
        }
        return characterRepository.save(transformCharacterInputDtoToCharacter(characterInputDto));
    }

    public void deleteCharacterById(Long characterId) {
        if (!characterRepository.existsById(characterId)) {
            throw new CharacterNotFoundException("Character with id: " + characterId + " not found!");
        }
        characterRepository.deleteById(characterId);
    }

    public void disassociateMovieAndCharacter(Long characterId, Long movieId) {
        var optionalCharacter = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        var optionalMovie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId + "!"));
        if (!optionalCharacter.getMoviesList().contains(optionalMovie)) {
            throw new BadRequestException("This movie is not associated with this character.");
        } else {
            optionalCharacter.getMoviesList().remove(optionalMovie);
            var updatedOptionalCharacter = characterRepository.save(optionalCharacter);
            transformCharacterToCharacterOutputDto(updatedOptionalCharacter);
        }
    }

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
        characterOutputDto.fileName = character.getFileName();
        characterOutputDto.moviesList = character.getMoviesList();
        return characterOutputDto;
    }

    public Character transformCharacterInputDtoToCharacter(CharacterInputDto characterInputDto) {
        var character = new Character();
        character.setCharacterAliasName(characterInputDto.getCharacterAliasName());
        character.setCharacterRealName(characterInputDto.getCharacterRealName());
        character.setCharacterActorName(characterInputDto.getCharacterActorName());
        character.setCharacterTitle(characterInputDto.getCharacterTitle());
        character.setCharacterGender(characterInputDto.getCharacterGender());
        character.setCharacterSummary(characterInputDto.getCharacterSummary());
        character.setCharacterDescription(characterInputDto.getCharacterDescription());
        return character;
    }
}
