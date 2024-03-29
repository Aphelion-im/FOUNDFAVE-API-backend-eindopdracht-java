package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.CharacterInputDto;
import online.foundfave.foundfaveapi.dtos.input.IdInputDto;
import online.foundfave.foundfaveapi.dtos.output.CharacterOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
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

    @PutMapping("/character/{characterId}")
    public ResponseEntity<String> updateCharacterById(@PathVariable("characterId") Long characterId, @Valid @RequestBody CharacterInputDto characterInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        return ResponseEntity.ok(characterService.updateCharacterById(characterId, characterInputDto));
    }

    @PutMapping("/associate/movie/{characterId}")
    public ResponseEntity<Object> associateMovieAndCharacter(@PathVariable("characterId") Long characterId, @Valid @RequestBody IdInputDto idInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        characterService.associateMovieAndCharacter(characterId, idInputDto.id);
        return ResponseEntity.ok().body("Movie with id: " + idInputDto.id + " is now associated with character with id: " + characterId + ".");
    }

    @PostMapping("")
    public ResponseEntity<Object> createCharacter(@Valid @RequestBody CharacterInputDto characterInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        Long newCharacterId = characterService.createCharacter(characterInputDto).getCharacterId();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newCharacterId).toUriString());
        return ResponseEntity.created(uri).body("New character added with id: " + newCharacterId + ".");
    }

    @DeleteMapping("/{characterId}")
    public ResponseEntity<Object> deleteCharacterById(@PathVariable("characterId") Long characterId) {
        try {
            characterService.deleteCharacterById(characterId);
        } catch (Exception e) {
            throw new BadRequestException("You are not allowed to delete this character as it is still linked to a movie or is a favorite.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Character with id: " + characterId + " deleted!");
    }

    @DeleteMapping("/disassociate/movie/{characterId}")
    public ResponseEntity<Object> disassociateMovieAndCharacter(@PathVariable("characterId") Long characterId, @Valid @RequestBody IdInputDto idInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        characterService.disassociateMovieAndCharacter(characterId, idInputDto.id);
        return ResponseEntity.ok().body("Movie with id: " + idInputDto.id + " is now disassociated from the character with id: " + characterId + ".");
    }
}
