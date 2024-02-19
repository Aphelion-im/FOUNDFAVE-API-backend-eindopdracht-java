package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.models.Character;
import online.foundfave.foundfaveapi.models.Movie;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.CharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static online.foundfave.foundfaveapi.enums.Gender.Male;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository characterRepository;

    Character character1;
    Character character2;
    List<Movie> moviesList;
    List<User> usersList;

    @BeforeEach
    void setUp() {
        moviesList = new ArrayList<>();
        usersList = new ArrayList<>();

        character1 = new Character(1L, "Alias name 1", "Real name 1", "Actor name 1", "Title 1", Male, "Summary 1", "Description 1", "Image url 1", "File name 1", moviesList, usersList);
        character2 = new Character(2L, "Alias name 2", "Real name 2", "Actor name 2", "Title 2", Male, "Summary 2", "Description 2", "Image url 2", "File name 2", moviesList, usersList);

        characterRepository.save(character1);
        characterRepository.save(character2);
    }

    @Test
    void getAllCharacters() throws Exception {
        mockMvc.perform(get("/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].characterId").value(character1.getCharacterId().toString()))
                .andExpect(jsonPath("$[0].characterAliasName").value("Alias name 1"))
                .andExpect(jsonPath("$[0].characterRealName").value("Real name 1"))
                .andExpect(jsonPath("$[0].characterActorName").value("Actor name 1"))
                .andExpect(jsonPath("$[0].characterTitle").value("Title 1"))
                .andExpect(jsonPath("$[0].characterGender").value(character1.getCharacterGender().toString()))
                .andExpect(jsonPath("$[0].characterSummary").value("Summary 1"))
                .andExpect(jsonPath("$[0].characterDescription").value("Description 1"))
                .andExpect(jsonPath("$[0].characterImageUrl").value("Image url 1"))
                .andExpect(jsonPath("$[0].characterImageUrl").value("Image url 1"))
                .andExpect(jsonPath("$[0].fileName").value("File name 1"))
                .andExpect(jsonPath("$[1].characterId").value(character2.getCharacterId().toString()))
                .andExpect(jsonPath("$[1].characterAliasName").value("Alias name 2"))
                .andExpect(jsonPath("$[1].characterRealName").value("Real name 2"))
                .andExpect(jsonPath("$[1].characterActorName").value("Actor name 2"))
                .andExpect(jsonPath("$[1].characterTitle").value("Title 2"))
                .andExpect(jsonPath("$[1].characterGender").value(character2.getCharacterGender().toString()))
                .andExpect(jsonPath("$[1].characterSummary").value("Summary 2"))
                .andExpect(jsonPath("$[1].characterDescription").value("Description 2"))
                .andExpect(jsonPath("$[1].characterImageUrl").value("Image url 2"))
                .andExpect(jsonPath("$[1].characterImageUrl").value("Image url 2"))
                .andExpect(jsonPath("$[1].fileName").value("File name 2"));
    }

    @Test
    void getCharacterById() {
    }
}