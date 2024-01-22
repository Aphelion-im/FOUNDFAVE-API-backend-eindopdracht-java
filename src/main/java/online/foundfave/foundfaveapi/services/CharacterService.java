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

    // Basic CRUD methods
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

    public Long createCharacter(CharacterInputDto characterInputDto) {
        Optional<Character> optionalCharacter = characterRepository.findByCharacterAliasNameIgnoreCase(characterInputDto.characterAliasName);
        if (optionalCharacter.isPresent()) {
            throw new CharacterAlreadyExistsException("Character: " + "'" + characterInputDto.characterAliasName + "'" + " already exists!");
        }
        Character newCharacter = characterRepository.save(transformCharacterInputDtoToCharacter(characterInputDto));
        return newCharacter.getCharacterId();
    }

    public String updateCharacter(Long characterId, CharacterInputDto characterInputDto) {
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        if (characterInputDto.characterAliasName != null)
            character.setCharacterAliasName(characterInputDto.getCharacterAliasName());
        if (characterInputDto.characterRealName != null)
            character.setCharacterRealName(characterInputDto.getCharacterRealName());
        if (characterInputDto.characterActorName != null)
            character.setCharacterActorName(characterInputDto.getCharacterActorName());
        if (characterInputDto.characterTitle != null)
            character.setCharacterTitle(characterInputDto.getCharacterTitle());
        if (characterInputDto.characterGender != null)
            character.setCharacterGender(characterInputDto.getCharacterGender());
        if (characterInputDto.characterSummary != null)
            character.setCharacterSummary(characterInputDto.getCharacterSummary());
        if (characterInputDto.characterDescription != null)
            character.setCharacterDescription(characterInputDto.getCharacterDescription());
        if (characterInputDto.characterImageUrl != null)
            character.setCharacterImageUrl(characterInputDto.getCharacterImageUrl());
        characterRepository.save(character);
        return "Character with id: " + character.getCharacterId() + " updated successfully!";
    }

    public void deleteCharacter(Long characterId) {
        if (!characterRepository.existsById(characterId)) {
            throw new CharacterNotFoundException("Character with id: " + characterId + " not found!");
        }
        try {
            characterRepository.deleteById(characterId);
        } catch (Exception e) {
            throw new BadRequestException("You are not allowed to delete this character as it is still linked to a movie or movies.");
        }
    }

    // Repository methods
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
            throw new CharacterNotFoundException("0 results. No actors were found!");
        }
        return characterOutputDtoList;
    }

    // Relational methods
    public void addMovieToCharacter(Long characterId, Long movieId) {
        var optionalCharacter = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        var optionalMovie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId + "!"));
        if (optionalCharacter.getMoviesList().contains(optionalMovie)) {
            throw new BadRequestException("This movie is already added to this character!");
        } else {
            optionalCharacter.getMoviesList().add(optionalMovie);
            var updatedOptionalCharacter = characterRepository.save(optionalCharacter);
            transformCharacterToCharacterOutputDto(updatedOptionalCharacter);
        }
    }

    public void removeMovieFromCharacter(Long characterId, Long movieId) {
        var optionalCharacter = characterRepository.findById(characterId).orElseThrow(() -> new CharacterNotFoundException("Character not found with id: " + characterId + "!"));
        var optionalMovie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId + "!"));
        if (!optionalCharacter.getMoviesList().contains(optionalMovie)) {
            throw new BadRequestException("This movie is not on this list or was already removed.");
        } else {
            optionalCharacter.getMoviesList().remove(optionalMovie);
            var updatedOptionalCharacter = characterRepository.save(optionalCharacter);
            transformCharacterToCharacterOutputDto(updatedOptionalCharacter);
        }
    }

    // Image methods


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
        characterOutputDto.moviesList = character.getMoviesList();
        return characterOutputDto;
    }

    // From CharacterInputDto to Character
    public Character transformCharacterInputDtoToCharacter(CharacterInputDto characterInputDto) {
        var character = new Character();
        character.setCharacterAliasName(characterInputDto.getCharacterAliasName());
        character.setCharacterRealName(characterInputDto.getCharacterRealName());
        character.setCharacterActorName(characterInputDto.getCharacterActorName());
        character.setCharacterTitle(characterInputDto.getCharacterTitle());
        character.setCharacterGender(characterInputDto.getCharacterGender());
        character.setCharacterSummary(characterInputDto.getCharacterSummary());
        character.setCharacterDescription(characterInputDto.getCharacterDescription());
        character.setCharacterImageUrl(characterInputDto.getCharacterImageUrl());
        return character;
    }
}
