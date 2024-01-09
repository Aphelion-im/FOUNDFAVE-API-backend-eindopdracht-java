package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.dtos.output.ContactFormOutputDto;
import online.foundfave.foundfaveapi.services.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // TODO: Refactor
    @GetMapping("")
    public ResponseEntity<List<CharacterOutputDto>> getAllCharacters() {
        List<CharacterOutputDto> characters = characterService.getCharacters();
        return ResponseEntity.ok(characters);
    }

    // TODO: Refactor
    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterOutputDto> getCharacter(@PathVariable Long characterId) {
        CharacterOutputDto characterOutputDto = characterService.getCharacter(characterId);
        return ResponseEntity.ok(characterOutputDto);
    }


}
