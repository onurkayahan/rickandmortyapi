package com.onurkayahan.rickandmorty.service;

import com.onurkayahan.rickandmorty.domain.GenericResponse;
import com.onurkayahan.rickandmorty.domain.Info;
import com.onurkayahan.rickandmorty.domain.episode.Episode;
import com.onurkayahan.rickandmorty.repository.EpisodeRepository;
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

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final HttpServletRequest httpServletRequest;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public EpisodeService(EpisodeRepository episodeRepository, HttpServletRequest httpServletRequest) {
        this.episodeRepository = episodeRepository;
        this.httpServletRequest = httpServletRequest;
    }

    public void saveAll(List<Episode> episodes) {
        episodeRepository.saveAll(episodes);
    }

    /**
     * Returns one page of episodes with info, also can be sortable
     * @param pageOpt
     * @param orderBy
     * @return
     */
    public GenericResponse<Episode> getEpisodeList(Optional<Integer> pageOpt, Optional<String> orderBy) {
        int page = pageOpt.orElse(1);
        Pageable pageable = orderBy.map(s -> PageRequest.of(pageOpt.orElse(1), 20, new Sort(Sort.Direction.ASC, s)))
                .orElseGet(() -> PageRequest.of(pageOpt.orElse(1), 20));
        Page<Episode> episodes = episodeRepository.findAll(pageable);
        if (episodes.isEmpty()) {
            logger.warn("Invalid page number" + page);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "{\"error\":\"There is nothing here\"}");
        }

        //Fetch request for obtain current URL
        String request = httpServletRequest.getRequestURL().append('?').append(httpServletRequest.getQueryString()).toString();

        Info info = new Info()
                .setNext(page == episodes.getTotalPages() ? null : request.replace("page=" + page, "page=" + (page + 1)))
                .setCount(episodes.getTotalElements())
                .setPrev(page == 0 || page == 1 ? null : request.replace("page=" + page, "page=" + (page - 1)))
                .setPages(episodes.getTotalPages());
        return new GenericResponse<Episode>()
                .setInfo(info)
                .setResults(episodes.getContent());
    }

    /**
     * Episode with id
     * @param id
     * @return Episode
     */
    public Episode getEpisodeWithId(Integer id) {
        Optional<Episode> episodeOptional = episodeRepository.findById(id);
        if (episodeOptional.isPresent()) {
            return episodeOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "{\"error\":\"Episode not found\"}");
        }
    }

}
