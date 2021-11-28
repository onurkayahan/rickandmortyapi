package com.onurkayahan.rickandmorty.service;

import com.onurkayahan.rickandmorty.domain.GenericResponse;
import com.onurkayahan.rickandmorty.domain.Info;
import com.onurkayahan.rickandmorty.domain.character.Character;
import com.onurkayahan.rickandmorty.repository.CharacterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final CharacterRepository characterRepository;
    private final HttpServletRequest httpServletRequest;

    public CharacterService(CharacterRepository characterRepository, HttpServletRequest httpServletRequest) {
        this.characterRepository = characterRepository;
        this.httpServletRequest = httpServletRequest;
    }

    public void saveAll(List<Character> characters) {
        try {
            characterRepository.saveAll(characters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all character names
     * @return List<String>
     */
    public List<String> getAllCharacterNamesWithoutPageable() {
        return characterRepository.findAll().stream().map(Character::getName)
                .collect(Collectors.toList());
    }

    /**
     * Returns one page of character with info, also can be sortable
     * @param pageOpt
     * @param orderBy
     * @return GenericResponse<Character>
     */
    public GenericResponse<Character> getCharacterList(Optional<Integer> pageOpt, Optional<String> orderBy) {
        int page = pageOpt.orElse(1);
        Pageable pageable = orderBy.map(s -> PageRequest.of(pageOpt.orElse(1), 20, new Sort(Sort.Direction.ASC, s)))
                .orElseGet(() -> PageRequest.of(pageOpt.orElse(1), 20));
        Page<Character> characters = characterRepository.findAll(pageable);
        if (characters.isEmpty()) {
            logger.warn("Invalid page number" + page);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "{\"error\":\"There is nothing here\"}");
        }
        //Fetch request for obtain current URL
        String request = httpServletRequest.getRequestURL().append('?').append(httpServletRequest.getQueryString()).toString();

        Info info = new Info()
                .setNext(page == characters.getTotalPages() ? null : request.replace("page=" + page, "page=" + (page + 1)))
                .setCount(characters.getTotalElements())
                .setPrev(page == 0 || page == 1 ? null : request.replace("page=" + page, "page=" + (page - 1)))
                .setPages(characters.getTotalPages());
        return new GenericResponse<Character>()
                .setInfo(info)
                .setResults(characters.getContent());
    }

    /**
     * Character with id
     * @param id
     * @return Character
     */
    public Character getCharacterWithId(Integer id) {
        Optional<Character> characterOptional = characterRepository.findById(id);
        if (characterOptional.isPresent()) {
            return characterOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "{\"error\":\"Character not found\"}");
        }
    }
}
