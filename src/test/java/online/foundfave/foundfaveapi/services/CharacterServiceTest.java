package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.CharacterInputDto;
import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.exceptions.CharacterAlreadyExistsException;
import online.foundfave.foundfaveapi.exceptions.CharacterNotFoundException;
import online.foundfave.foundfaveapi.models.Character;
import online.foundfave.foundfaveapi.models.Movie;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static online.foundfave.foundfaveapi.enums.Gender.Female;
import static online.foundfave.foundfaveapi.enums.Gender.Male;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CharacterServiceTest {

    @Mock
    CharacterRepository characterRepository;

    @InjectMocks
    CharacterService characterService;

    @Captor
    ArgumentCaptor<Character> characterArgumentCaptor;

    Character character1;
    Character character2;
    Character createdCharacter;
    Character character4;
    CharacterInputDto characterInputDto;
    Movie movie1;
    Movie movie2;
    List<Character> charactersList;
    List<Movie> moviesList;
    List<User> usersList;

    @BeforeEach
    void setUp() {
        moviesList = new ArrayList<>();
        usersList = new ArrayList<>();
        charactersList = new ArrayList<>();

        character1 = new Character(1L, "Alias name 1", "Real name 1", "Actor name 1", "Title 1", Male, "Summary 1", "Description 1", "Image url 1", "File name 1", moviesList, usersList);
        character2 = new Character(2L, "Alias name 2", "Real name 2", "Actor name 2", "Title 2", Male, "Summary 2", "Description 2", "Image url 2", "File name 2", moviesList, usersList);
        createdCharacter = new Character(3L, "Alias name 3", "Real name 3", "Actor name 3", "Title 3", Female, "Summary 3", "Description 3", "Image url 3", "File name 3", moviesList, usersList);
        characterInputDto = new CharacterInputDto("Alias name 4", "Real name 4", "Actor name 4", "Title 4", Female, "Summary 4", "Description 4");

        character4 = new Character();
        character4.setCharacterId(4L);

        charactersList.add(character1);
        charactersList.add(character2);
        charactersList.add(createdCharacter);

        movie1 = new Movie(1L, "Movie Title", "Movie summary", "01-01-2022", "Image url", "File name", charactersList);
        movie2 = new Movie(2L, "Movie Title", "Movie summary", "01-01-2022", "Image url", "File name", charactersList);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should get all characters")
    void shouldGetAllCharacters() {
        // Arrange
        List<Character> charactersList = Arrays.asList(character1, character2);

        when(characterRepository.findAll()).thenReturn(charactersList);

        // Act
        List<CharacterOutputDto> result = characterService.getAllCharacters();

        // Assert
        assertEquals(charactersList.size(), result.size());
        assertEquals(character1.getCharacterId(), result.get(0).getCharacterId());
        assertEquals(character2.getCharacterId(), result.get(1).getCharacterId());
    }


    @Test
    @DisplayName("Should get a character by ID")
    void shouldGetCharacterById() {
        Long characterId = 2L;
        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character2));

        Character character = characterRepository.findById(characterId).get();
        CharacterOutputDto characterOutputDto = characterService.getCharacterById(characterId);

        assertEquals(character.getCharacterAliasName(), characterOutputDto.characterAliasName);
        assertEquals(character.getCharacterActorName(), characterOutputDto.characterActorName);
        assertEquals(character.getCharacterDescription(), characterOutputDto.characterDescription);
        assertEquals(character.getCharacterSummary(), characterOutputDto.characterSummary);
        assertEquals(character.getCharacterRealName(), characterOutputDto.characterRealName);
        assertEquals(character.getCharacterTitle(), characterOutputDto.characterTitle);
        assertEquals(character.getCharacterGender(), characterOutputDto.characterGender);
    }

    @Test
    @DisplayName("Find characters by name starting with")
    void shouldFindCharactersByNameStartingWith() {
        String characterName = "Hulk";
        when(characterRepository.findByCharacterAliasNameStartingWithIgnoreCase(characterName)).thenReturn(charactersList);
        List<CharacterOutputDto> characters = characterService.findCharactersByNameStartingWith(characterName);
        assertEquals(charactersList.size(), characters.size());
    }

    @Test
    @DisplayName("Should Throw Character not found exception")
    void shouldFindCharactersByNameStartingWithException() {
        String characterName = "Hulk";
        List<Character> characterList = new ArrayList<>();
        when(characterRepository.findByCharacterAliasNameStartingWithIgnoreCase(characterName)).thenReturn(characterList);
        assertThrows(CharacterNotFoundException.class, () -> characterService.findCharactersByNameStartingWith(characterName));
    }


    @Test
    @DisplayName("Find characters by name contains")
    void shouldFindCharactersByNameContains() {
        // Arrange
        String characterName = "Hulk";
        when(characterRepository.findByCharacterAliasNameContainsIgnoreCase(characterName)).thenReturn(charactersList);

        // Act
        List<CharacterOutputDto> characters = characterService.findCharactersByNameContains(characterName);

        // Assert
        assertEquals(charactersList.size(), characters.size());
    }

    @Test
    @DisplayName("Find characters by name Sorted Ascending")
    void shouldFindCharactersByNameSortedAsc() {
        // Arrange
        String characterName = "Hulk";
        when(characterRepository.findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameAsc(characterName)).thenReturn(charactersList);

        // Act
        List<CharacterOutputDto> characters = characterService.findCharactersByNameSortedAsc(characterName);

        // Assert
        assertEquals(charactersList.size(), characters.size());
    }

    @Test
    @DisplayName("Should find characters by name Sorted Descending")
    void shouldFindCharactersByNameSortedDesc() {
        // Arrange
        String characterName = "Hulk";
        when(characterRepository.findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameDesc(characterName)).thenReturn(charactersList);

        // Act
        List<CharacterOutputDto> characters = characterService.findCharactersByNameSortedDesc(characterName);

        // Assert
        assertEquals(charactersList.size(), characters.size());
    }

    @Test
    @DisplayName("Should find characters by actors name contains")
    void shouldFindCharactersByActorNameContains() {
        // Arrange
        String characterName = "Hulk";
        when(characterRepository.findByCharacterActorNameContainsIgnoreCase(characterName)).thenReturn(charactersList);

        // Act
        List<CharacterOutputDto> characters = characterService.findCharactersByActorNameContains(characterName);

        // Assert
        assertEquals(charactersList.size(), characters.size());
    }

    @Test
    @DisplayName("Should update character by ID")
    @Disabled
    void shouldUpdateCharacterById() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Should associate movie and character")
    @Disabled
    void shouldAssociateMovieAndCharacter() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Should create a character")
    void shouldCreateCharacter() {
        // Arrange
        when(characterRepository.save(any(Character.class))).thenReturn(createdCharacter);

        // Act
        Character testCharacter = characterService.createCharacter(characterInputDto);

        // Assert
        assertEquals(createdCharacter.getCharacterId(), testCharacter.getCharacterId());
        assertEquals(createdCharacter.getCharacterAliasName(), testCharacter.getCharacterAliasName());
        assertEquals(createdCharacter.getCharacterRealName(), testCharacter.getCharacterRealName());
        assertEquals(createdCharacter.getCharacterActorName(), testCharacter.getCharacterActorName());
        assertEquals(createdCharacter.getCharacterTitle(), testCharacter.getCharacterTitle());
        assertEquals(createdCharacter.getCharacterGender(), testCharacter.getCharacterGender());
        assertEquals(createdCharacter.getCharacterSummary(), testCharacter.getCharacterSummary());
        assertEquals(createdCharacter.getCharacterDescription(), testCharacter.getCharacterDescription());
    }

    @Test
    @DisplayName("Should not create Character If Character is already present")
    void shouldNotCreateCharacterIfCharacterIsAlreadyPresent() {
        when(characterRepository.findByCharacterAliasNameIgnoreCase(any())).thenReturn(Optional.of(character1));
        assertThrows(CharacterAlreadyExistsException.class, () -> characterService.createCharacter(characterInputDto));
    }

    @Test
    @DisplayName("Should throw CharacterNotFoundException")
    void shouldThrowCharacterNotFoundException() {
        Long characterId = 4L;
        when(characterRepository.findById(characterId)).thenReturn(Optional.empty());
        assertThrows(CharacterNotFoundException.class, () -> characterService.deleteCharacterById(characterId));
    }

    @Test
    void deleteCharacter() {
        // Arrange
        Long characterId = 1L;
        when(characterRepository.existsById(characterId)).thenReturn(true);

        // Act
        characterService.deleteCharacterById(characterId);

        // Assert
        verify(characterRepository).deleteById(characterId);
    }


    @Test
    @DisplayName("Should disassociate Movie and Character")
    @Disabled
    void shouldDisassociateMovieAndCharacter() {
        // Arrange


        // Act


        // Assert
    }
}