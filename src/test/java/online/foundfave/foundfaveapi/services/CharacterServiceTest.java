package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.CharacterInputDto;
import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.CharacterAlreadyExistsException;
import online.foundfave.foundfaveapi.exceptions.CharacterNotFoundException;
import online.foundfave.foundfaveapi.models.Character;
import online.foundfave.foundfaveapi.models.Movie;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import online.foundfave.foundfaveapi.repositories.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static online.foundfave.foundfaveapi.enums.Gender.Female;
import static online.foundfave.foundfaveapi.enums.Gender.Male;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CharacterServiceTest {

    @Mock
    CharacterRepository characterRepository;

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    CharacterService characterService;

    @Captor
    ArgumentCaptor<Character> characterArgumentCaptor;

    Character character1;
    Character character2;
    Character character3;
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
        character3 = new Character(3L, "Alias name 3", "Real name 3", "Actor name 3", "Title 3", Female, "Summary 3", "Description 3", "Image url 3", "File name 3", moviesList, usersList);
        characterInputDto = new CharacterInputDto("Alias name 4", "Real name 4", "Actor name 4", "Title 4", Female, "Summary 4", "Description 4");

        charactersList.add(character1);
        charactersList.add(character2);
        charactersList.add(character3);

        movie1 = new Movie(1L, "Movie Title 1", "Movie summary 1", "01-01-2022", "Image url 1", "File name 1", charactersList);
        movie2 = new Movie(2L, "Movie Title 2", "Movie summary 2", "01-01-2022", "Image url 2", "File name 2", charactersList);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should get all characters")
    void shouldGetAllCharacters() {
        when(characterRepository.findAll()).thenReturn(charactersList);
        List<CharacterOutputDto> result = characterService.getAllCharacters();
        assertEquals(charactersList.size(), result.size());
        assertEquals(1L, result.get(0).getCharacterId());
        assertEquals(character2.getCharacterId(), result.get(1).getCharacterId());
        assertEquals(character3.getCharacterId(), result.get(2).getCharacterId());
    }

    @Test
    @DisplayName("Should get a character by ID")
    void shouldGetCharacterById() {
        Long characterId = 2L;
        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character2));
        CharacterOutputDto characterOutputDto = characterService.getCharacterById(characterId);
        assertEquals(characterId, characterOutputDto.characterId);
        assertEquals("Alias name 2", characterOutputDto.characterAliasName);
        assertEquals("Real name 2", characterOutputDto.characterRealName);
        assertEquals("Actor name 2", characterOutputDto.characterActorName);
        assertEquals("Title 2", characterOutputDto.characterTitle);
        assertEquals(Male, characterOutputDto.characterGender);
        assertEquals("Summary 2", characterOutputDto.characterSummary);
        assertEquals("Description 2", characterOutputDto.characterDescription);
    }

    @Test
    @DisplayName("Find characters by name starting with")
    void shouldFindCharactersByAliasNameStartingWith() {
        String characterName = "Alias";
        when(characterRepository.findByCharacterAliasNameStartingWithIgnoreCase(characterName)).thenReturn(charactersList);
        List<CharacterOutputDto> charactersResult = characterService.findCharactersByNameStartingWith(characterName);
        assertEquals(character1.getCharacterAliasName(), charactersResult.get(0).getCharacterAliasName());
        assertEquals(character2.getCharacterAliasName(), charactersResult.get(1).getCharacterAliasName());
        assertEquals(character3.getCharacterAliasName(), charactersResult.get(2).getCharacterAliasName());
    }

    @Test
    @DisplayName("Find characters by name starting with: Should throw character not found exception")
    void shouldFindCharactersByNameStartingWithThrowsException() {
        assertThrows(CharacterNotFoundException.class, () -> characterService.findCharactersByNameStartingWith(null));
    }

    @Test
    @DisplayName("Find characters by name contains")
    void shouldFindCharactersByAliasNameContains() {
        String characterName = "Alias";
        when(characterRepository.findByCharacterAliasNameContainsIgnoreCase(characterName)).thenReturn(charactersList);
        List<CharacterOutputDto> charactersResult = characterService.findCharactersByNameContains(characterName);
        assertEquals(character1.getCharacterAliasName(), charactersResult.get(0).getCharacterAliasName());
        assertEquals(character2.getCharacterAliasName(), charactersResult.get(1).getCharacterAliasName());
        assertEquals(character3.getCharacterAliasName(), charactersResult.get(2).getCharacterAliasName());
    }

    @Test
    @DisplayName("Find characters by name contains: Should throw character not found exception")
    void shouldFindCharactersByNameContainsThrowsException() {
        assertThrows(CharacterNotFoundException.class, () -> characterService.findCharactersByNameContains(null));
    }

    @Test
    @DisplayName("Find characters by name Sorted Ascending")
    void shouldFindCharactersByAliasNameSortedAsc() {
        String characterName = "Alias";
        when(characterRepository.findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameAsc(characterName)).thenReturn(charactersList);
        List<CharacterOutputDto> charactersResult = characterService.findCharactersByNameSortedAsc(characterName);
        assertEquals(character1.getCharacterAliasName(), charactersResult.get(0).getCharacterAliasName());
        assertEquals(character2.getCharacterAliasName(), charactersResult.get(1).getCharacterAliasName());
        assertEquals(character3.getCharacterAliasName(), charactersResult.get(2).getCharacterAliasName());
    }

    @Test
    @DisplayName("Find characters by name Sorted Ascending: Should throw character not found exception")
    void shouldFindCharactersByNameSortedAscThrowsException() {
        assertThrows(CharacterNotFoundException.class, () -> characterService.findCharactersByNameSortedAsc(null));
    }

    @Test
    @DisplayName("Should find characters by name Sorted Descending")
    void shouldFindCharactersByAliasNameSortedDesc() {
        String characterName = "Hulk";
        when(characterRepository.findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameDesc(characterName)).thenReturn(charactersList);
        List<CharacterOutputDto> charactersResult = characterService.findCharactersByNameSortedDesc(characterName);
        assertEquals(character1.getCharacterAliasName(), charactersResult.get(0).getCharacterAliasName());
        assertEquals(character2.getCharacterAliasName(), charactersResult.get(1).getCharacterAliasName());
        assertEquals(character3.getCharacterAliasName(), charactersResult.get(2).getCharacterAliasName());
    }

    @Test
    @DisplayName("Should find characters by name Sorted Descending: Should throw character not found exception")
    void shouldFindCharactersByNameSortedDescThrowsException() {
        assertThrows(CharacterNotFoundException.class, () -> characterService.findCharactersByNameSortedDesc(null));
    }

    @Test
    @DisplayName("Should find characters by actors name contains")
    void shouldFindCharactersByActorNameContains() {
        String characterName = "Hulk";
        when(characterRepository.findByCharacterActorNameContainsIgnoreCase(characterName)).thenReturn(charactersList);
        List<CharacterOutputDto> charactersResult = characterService.findCharactersByActorNameContains(characterName);
        assertEquals(character1.getCharacterActorName(), charactersResult.get(0).getCharacterActorName());
        assertEquals(character2.getCharacterActorName(), charactersResult.get(1).getCharacterActorName());
        assertEquals(character3.getCharacterActorName(), charactersResult.get(2).getCharacterActorName());
    }

    @Test
    @DisplayName("Should find characters by actors name contains: Should throw character not found exception")
    void shouldFindCharactersByActorNameContainsThrowsException() {
        assertThrows(CharacterNotFoundException.class, () -> characterService.findCharactersByActorNameContains(null));
    }

    @Test
    @DisplayName("Should update character by ID")
    void shouldUpdateCharacterById() {
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character1));
        when(characterRepository.save(characterService.transformCharacterInputDtoToCharacter(characterInputDto))).thenReturn(character1);
        characterService.updateCharacterById(1L, characterInputDto);
        verify(characterRepository, times(1)).save(characterArgumentCaptor.capture());
        Character capturedCharacter = characterArgumentCaptor.getValue();
        assertEquals(characterInputDto.characterAliasName, capturedCharacter.getCharacterAliasName());
        assertEquals(characterInputDto.characterRealName, capturedCharacter.getCharacterRealName());
        assertEquals(characterInputDto.characterActorName, capturedCharacter.getCharacterActorName());
        assertEquals(characterInputDto.characterTitle, capturedCharacter.getCharacterTitle());
        assertEquals(characterInputDto.characterGender, capturedCharacter.getCharacterGender());
        assertEquals(characterInputDto.characterSummary, capturedCharacter.getCharacterSummary());
        assertEquals(characterInputDto.characterDescription, capturedCharacter.getCharacterDescription());
    }

    @Test
    @DisplayName("Should associate movie and character adding 1 movie to empty list")
    void shouldAssociateMovieAndCharacter() {
        Long characterId = 1L;
        Long movieId = 1L;
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character1));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
        when(characterRepository.save(character1)).thenReturn(character1);
        when(movieRepository.save(movie1)).thenReturn(movie1);
        CharacterOutputDto characterOutputDtoResult = characterService.associateMovieAndCharacter(characterId, movieId);
        assertEquals(1, characterOutputDtoResult.getMoviesList().size());
    }

    @Test
    @DisplayName("Associate movie and character: Should throw BadRequestException")
    void associateMovieAndCharacterShouldThrowException() {
        Long characterId = 1L;
        Long movieId = 1L;
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character1));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
        when(characterRepository.save(character1)).thenReturn(character1);
        when(movieRepository.save(movie1)).thenReturn(movie1);
        characterService.associateMovieAndCharacter(characterId, movieId);
        assertThrows(BadRequestException.class, () -> characterService.associateMovieAndCharacter(characterId, movieId));
    }

    @Test
    @DisplayName("Should create a character")
    void shouldCreateCharacter() {
        when(characterRepository.save(any(Character.class))).thenReturn(character1);
        characterService.createCharacter(characterInputDto);
        verify(characterRepository, times(1)).save(characterArgumentCaptor.capture());
        Character characterArgumentCaptorValue = characterArgumentCaptor.getValue();
        assertNotEquals(character1.getCharacterAliasName(), characterArgumentCaptorValue.getCharacterAliasName());
        assertNotEquals(character1.getCharacterRealName(), characterArgumentCaptorValue.getCharacterRealName());
        assertNotEquals(character1.getCharacterActorName(), characterArgumentCaptorValue.getCharacterActorName());
        assertNotEquals(character1.getCharacterTitle(), characterArgumentCaptorValue.getCharacterTitle());
        assertNotEquals(character1.getCharacterGender(), characterArgumentCaptorValue.getCharacterGender());
        assertNotEquals(character1.getCharacterSummary(), characterArgumentCaptorValue.getCharacterSummary());
        assertNotEquals(character1.getCharacterDescription(), characterArgumentCaptorValue.getCharacterDescription());
    }

    @Test
    @DisplayName("Should not create Character If Character is already present: Should throw CharacterAlreadyExistsException")
    void shouldNotCreateCharacterIfCharacterIsAlreadyPresentThrowsException() {
        when(characterRepository.findByCharacterAliasNameIgnoreCase(any())).thenReturn(Optional.of(character1));
        assertThrows(CharacterAlreadyExistsException.class, () -> characterService.createCharacter(characterInputDto));
    }

    @Test
    @DisplayName("Should delete character by ID")
    void shouldDeleteCharacterById() {
        Long characterId = 1L;
        when(characterRepository.existsById(characterId)).thenReturn(true);
        characterService.deleteCharacterById(characterId);
        verify(characterRepository, times(1)).deleteById(characterId);
        assertFalse(characterRepository.findById(characterId).isPresent());
    }

    @Test
    @DisplayName("Should throw CharacterNotFoundException")
    void shouldThrowCharacterNotFoundException() {
        Long characterId = 1L;
        when(characterRepository.findById(characterId)).thenReturn(Optional.empty());
        assertThrows(CharacterNotFoundException.class, () -> characterService.deleteCharacterById(characterId));
    }

    @Test
    @DisplayName("Should disassociate Movie and Character removing 1 movie leaving an empty list")
    void shouldDisassociateMovieAndCharacter() {
        Long characterId = 1L;
        Long movieId = 1L;
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character1));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
        when(characterRepository.save(character1)).thenReturn(character1);
        when(movieRepository.save(movie1)).thenReturn(movie1);
        characterService.associateMovieAndCharacter(characterId, movieId);
        CharacterOutputDto characterOutputDtoResult = characterService.disassociateMovieAndCharacter(characterId, movieId);
        assertEquals(0, characterOutputDtoResult.getMoviesList().size());
    }

    @Test
    @DisplayName("Disassociate movie and character: Should throw BadRequestException")
    void disassociateMovieAndCharacterShouldThrowException() {
        Long characterId = 1L;
        Long movieId = 1L;
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character1));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
        when(characterRepository.save(character1)).thenReturn(character1);
        when(movieRepository.save(movie1)).thenReturn(movie1);
        characterService.associateMovieAndCharacter(characterId, movieId);
        characterService.disassociateMovieAndCharacter(characterId, movieId);
        assertThrows(BadRequestException.class, () -> characterService.disassociateMovieAndCharacter(characterId, movieId));
    }
}