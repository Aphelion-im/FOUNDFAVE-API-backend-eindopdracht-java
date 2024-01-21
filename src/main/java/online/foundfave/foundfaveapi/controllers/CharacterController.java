package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.CharacterInputDto;
import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.services.CharacterService;
import online.foundfave.foundfaveapi.utilities.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        List<CharacterOutputDto> characterOutputDtoList = characterService.getAllCharacters();
        return ResponseEntity.ok(characterOutputDtoList);
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterOutputDto> getCharacterById(@PathVariable Long characterId) {
        CharacterOutputDto characterOutputDto = characterService.getCharacterById(characterId);
        return ResponseEntity.ok(characterOutputDto);
    }

    @PostMapping("")
    public ResponseEntity<Object> createCharacter(@Valid @RequestBody CharacterInputDto characterInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        Long newCharacterId = characterService.createCharacter(characterInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newCharacterId).toUriString());
        return ResponseEntity.created(uri).body("New character added with id: " + newCharacterId + ".");
    }

    @PutMapping("/character/{characterId}")
    public ResponseEntity<String> updateCharacter(@PathVariable("characterId") Long characterId, @Valid @RequestBody CharacterInputDto characterInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        return ResponseEntity.ok(characterService.updateCharacter(characterId, characterInputDto));
    }

    @DeleteMapping("/{characterId}")
    public ResponseEntity<Object> deleteCharacter(@PathVariable("characterId") Long characterId) {
        characterService.deleteCharacter(characterId);
        return ResponseEntity.status(HttpStatus.OK).body("Character with id: " + characterId + " deleted!");
    }

    // Repository methods
    @GetMapping("/search/starting-with")
    public ResponseEntity<List<CharacterOutputDto>> findCharactersByNameStartingWith(@RequestParam("name") String name) {
        List<CharacterOutputDto> characterOutputDtoList = characterService.findCharactersByNameStartingWith(name);
        return ResponseEntity.ok(characterOutputDtoList);
    }

    @GetMapping("/search/contains")
    public ResponseEntity<List<CharacterOutputDto>> findCharactersByNameContains(@RequestParam("name") String name) {
        List<CharacterOutputDto> characterOutputDtoList = characterService.findCharactersByNameContains(name);
        return ResponseEntity.ok(characterOutputDtoList);
    }

    @GetMapping("/search/sorted-asc")
    public ResponseEntity<List<CharacterOutputDto>> findCharactersByNameSortedAsc(@RequestParam("name") String name) {
        List<CharacterOutputDto> characterOutputDtoList = characterService.findCharactersByNameSortedAsc(name);
        return ResponseEntity.ok(characterOutputDtoList);
    }

    @GetMapping("/search/sorted-desc")
    public ResponseEntity<List<CharacterOutputDto>> findCharactersByNameSortedDesc(@RequestParam("name") String name) {
        List<CharacterOutputDto> characterOutputDtoList = characterService.findCharactersByNameSortedDesc(name);
        return ResponseEntity.ok(characterOutputDtoList);
    }

    @GetMapping("/search/actor-name")
    public ResponseEntity<List<CharacterOutputDto>> findCharactersByActorNameContains(@RequestParam("name") String name) {
        List<CharacterOutputDto> characterOutputDtoList = characterService.findCharactersByActorNameContains(name);
        return ResponseEntity.ok(characterOutputDtoList);
    }

    // Relational methods
    // TODO: Add movie to character



    // TODO: Remove movie from character




    // Image methods
    // TODO: Upload character photo


}
