package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.services.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // Basic CRUD methods


    // TODO: Refactor
    @GetMapping("")
    public ResponseEntity<List<CharacterOutputDto>> getAllCharacters() {
        List<CharacterOutputDto> characters = characterService.getCharacters();
        return ResponseEntity.ok(characters);
    }

    // TODO: Refactor
    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterOutputDto> getCharacterById(@PathVariable Long characterId) {
        CharacterOutputDto characterOutputDto = characterService.getCharacterById(characterId);
        return ResponseEntity.ok(characterOutputDto);
    }


    // Repository methods
    @GetMapping("/search")
    public ResponseEntity<List<CharacterOutputDto>> getCharacterByName(@RequestParam("name") String name) {
        List<CharacterOutputDto> characters = characterService.getCharacterByName(name);
        return ResponseEntity.ok(characters);
    }


// TODO: Add character. Let ook op Gender Enum invoeren


    // TODO: Update character

// TODO: Delete character


// TODO: Add character to movie. Add movie to character


// TODO: findByNameOrderByNameDesc
// TODO: findByNameOrderByNameASC

    // Relational methods




    // Image methods
    // TODO: Upload character photo


}
