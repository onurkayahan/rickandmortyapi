package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.domain.GenericResponse;
import com.egemsoft.rickandmorty.domain.character.Character;
import com.egemsoft.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/character")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping()
    public GenericResponse<Character> getCharacterList(@RequestParam Optional<Integer> page,
                                                       @RequestParam Optional<String> orderBy) {

        return characterService.getCharacterList(page, orderBy);
    }

    @GetMapping("{id}")
    public Character getCharacterWithId(@PathVariable Integer id) {
        return characterService.getCharacterWithId(id);
    }
}
