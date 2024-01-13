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
    @GetMapping("")
    public ResponseEntity<List<CharacterOutputDto>> getAllCharacters() {
        List<CharacterOutputDto> characters = characterService.getCharacters();
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterOutputDto> getCharacterById(@PathVariable Long characterId) {
        CharacterOutputDto characterOutputDto = characterService.getCharacterById(characterId);
        return ResponseEntity.ok(characterOutputDto);
    }


    // TODO: Add character. Let ook op Gender Enum invoeren


    // TODO: Update character

// TODO: Delete character


    // Repository methods
    @GetMapping("/search")
    public ResponseEntity<List<CharacterOutputDto>> findCharacterByName(@RequestParam("name") String name) {
        List<CharacterOutputDto> characters = characterService.findCharacterByName(name);
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/search/asc")
    public ResponseEntity<List<CharacterOutputDto>> findCharacterByNameSortedAsc(@RequestParam("name") String name) {
        List<CharacterOutputDto> characters = characterService.findCharacterByNameSortedAsc(name);
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/search/desc")
    public ResponseEntity<List<CharacterOutputDto>> findCharacterByNameSortedDesc(@RequestParam("name") String name) {
        List<CharacterOutputDto> characters = characterService.findCharacterByNameSortedDesc(name);
        return ResponseEntity.ok(characters);
    }




    // Relational methods
// TODO: Add character to movie. Add movie to character


    // Image methods
    // TODO: Upload character photo


}
