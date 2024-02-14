package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.models.Character;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    CharacterRepository characterRepository;

    @InjectMocks
    CharacterService characterService;


//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    @DisplayName("Get all characters")
    void getAllCharacters() {
        // Arrange
        Character character1 = new Character();
        character1.setCharacterId(1L);

        Character character2 = new Character();
        character1.setCharacterId(2L);

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
    @DisplayName("Get character by ID")
    void getCharacterById() {
        // Arrange
        Character character = new Character();
        character.setCharacterId(1L);

        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));

        // Act
        CharacterOutputDto characterOutputDto = characterService.getCharacterById(1L);

        // Assert
        assertEquals(characterOutputDto.getCharacterId(), character.getCharacterId());

    }

    @Test
    @DisplayName("Find characters by name starting with")
    @Disabled
    void findCharactersByNameStartingWith() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Find characters by name contains")
    @Disabled
    void findCharactersByNameContains() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Find characters by name Sorted Ascending")
    @Disabled
    void findCharactersByNameSortedAsc() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Find characters by name Sorted Descending")
    @Disabled
    void findCharactersByNameSortedDesc() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Find characters by actors name contains")
    @Disabled
    void findCharactersByActorNameContains() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Update character by ID")
    @Disabled
    void updateCharacterById() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Associate movie and character")
    @Disabled
    void associateMovieAndCharacter() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Create character")
    @Disabled
    void createCharacter() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Delete character by ID")
    @Disabled
    void deleteCharacterById() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    @DisplayName("Disassociate Movie and Character")
    @Disabled
    void disassociateMovieAndCharacter() {
        // Arrange


        // Act


        // Assert
    }

//    @Test
//    @DisplayName("Transform Character To CharacterOutputDto")
//    @Disabled
//    void transformCharacterToCharacterOutputDto() {
//        // Arrange
//
//
//        // Act
//
//
//        // Assert
//    }
//
//    @Test
//    @DisplayName("Transform CharacterInputDto To Character")
//    @Disabled
//    void transformCharacterInputDtoToCharacter() {
//        // Arrange
//
//
//        // Act
//
//
//        // Assert
//    }
}