package com.onurkayahan.rickandmorty.service;

import com.onurkayahan.rickandmorty.domain.Info;
import com.onurkayahan.rickandmorty.domain.SystemInfo;
import com.onurkayahan.rickandmorty.domain.character.Character;
import com.onurkayahan.rickandmorty.domain.episode.Episode;
import com.onurkayahan.rickandmorty.domain.location.Location;
import com.onurkayahan.rickandmorty.repository.SystemInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
@Order(1)
public class InitService {


    private final CharacterService characterService;
    private final EpisodeService episodeService;
    private final LocationService locationService;
    private final SystemInfoRepository systemInfoRepository;

    @Qualifier("apiClient")
    private final RestTemplate apiClient;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String APIURL = "https://rickandmortyapi.com/api";

    public InitService(CharacterService characterService, EpisodeService episodeService, LocationService locationService, SystemInfoRepository systemInfoRepository, RestTemplate apiClient) {
        this.characterService = characterService;
        this.episodeService = episodeService;
        this.locationService = locationService;
        this.systemInfoRepository = systemInfoRepository;
        this.apiClient = apiClient;
    }

    /**
     * Update DB at 00:00 everyday
     */
    @Scheduled(cron = "0 0 0 1/1 * ?")
    @PostConstruct
    public void getApiUrlsAndStoreData() throws ParseException {
        String jsonResponse = apiClient.getForObject(APIURL, String.class);
        JSONObject objectResponse = (JSONObject) new JSONParser().parse(jsonResponse);
        String charactersApiUrl = objectResponse.get("characters").toString();
        String episodesApiUrl = objectResponse.get("episodes").toString();
        String locationsApiUrl = objectResponse.get("locations").toString();
        logger.info("Fetching data from " + APIURL + "      Loading");
        getCharactersAndImportToDb(charactersApiUrl);
        logger.info("Fetching data from " + APIURL + "      Loading.");
        getEpisodesAndImportToDb(episodesApiUrl);
        logger.info("Fetching data from " + APIURL + "      Loading..");
        getLocationsAndImportToDb(locationsApiUrl);
        logger.info("Fetching data from " + APIURL + "      Loading...");
        SystemInfo systemInfo = new SystemInfo()
                .setLastUpdatedDateOfDb(new Date());

        //lets make it single row document
        systemInfo.setId(1);

        systemInfoRepository.save(systemInfo);
        logger.info("Fetched data from " + APIURL + "       updatedDate: " + systemInfo.getLastUpdatedDateOfDb());
    }

    private void getCharactersAndImportToDb(String charactersApiUrl) throws ParseException {
        Info info = null;
        int page = 1;
        do {
            String jsonResponse = apiClient.getForObject(charactersApiUrl + "?page=" + page, String.class);
            JSONObject responseObject = (JSONObject) new JSONParser().parse(jsonResponse);
            String charactersObject = responseObject.get("results").toString();
            info = getInfoFromResponseObject(responseObject);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter();

            try {
                List<Character> characters = objectMapper.readValue(charactersObject, objectMapper.getTypeFactory().constructCollectionType(List.class, Character.class));
                characterService.saveAll(characters);
            } catch (IOException e) {
                e.printStackTrace();
            }

            page++;
        } while (info != null && info.getNext() != null && page != (info.getPages() + 1));
    }

    private void getEpisodesAndImportToDb(String episodesApiUrl) throws ParseException {
        Info info = null;
        int page = 1;
        do {
            String jsonResponse = apiClient.getForObject(episodesApiUrl + "?page=" + page, String.class);
            JSONObject responseObject = (JSONObject) new JSONParser().parse(jsonResponse);
            String charactersObject = responseObject.get("results").toString();
            info = getInfoFromResponseObject(responseObject);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter();

            try {
                List<Episode> episodes = objectMapper.readValue(charactersObject, objectMapper.getTypeFactory().constructCollectionType(List.class, Episode.class));
                episodeService.saveAll(episodes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            page++;
        } while (info != null && info.getNext() != null && page != (info.getPages() + 1));
    }

    private void getLocationsAndImportToDb(String locationsApiUrl) throws ParseException {
        Info info = null;
        int page = 1;
        do {
            String jsonResponse = apiClient.getForObject(locationsApiUrl + "?page=" + page, String.class);
            JSONObject responseObject = (JSONObject) new JSONParser().parse(jsonResponse);
            String charactersObject = responseObject.get("results").toString();
            info = getInfoFromResponseObject(responseObject);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter();

            try {
                List<Location> locations = objectMapper.readValue(charactersObject, objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, Location.class));
                locationService.saveAll(locations);
            } catch (IOException e) {
                e.printStackTrace();
            }

            page++;
        } while (info != null && info.getNext() != null && page != (info.getPages() + 1));
    }

    private Info getInfoFromResponseObject(JSONObject responseObject) {
        JSONObject infoObject = (JSONObject) responseObject.get("info");
        return new Info()
                .setNext(infoObject.get("next") != null ? infoObject.get("next").toString() : null)
                .setPages(Integer.parseInt(infoObject.get("pages").toString()));
    }
}
