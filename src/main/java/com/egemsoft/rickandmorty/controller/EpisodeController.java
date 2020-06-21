package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.domain.GenericResponse;
import com.egemsoft.rickandmorty.domain.episode.Episode;
import com.egemsoft.rickandmorty.service.EpisodeService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/episode")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping()
    public GenericResponse<Episode> getEpisodeList(@RequestParam Optional<Integer> page,
                                                   @RequestParam Optional<String> orderBy) {
        return episodeService.getEpisodeList(page,orderBy);
    }

    @GetMapping("{id}")
    public Episode getCharacterWithId(@PathVariable Integer id) {
        return episodeService.getEpisodeWithId(id);
    }
}
