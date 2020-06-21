package com.egemsoft.rickandmorty;

import com.egemsoft.rickandmorty.domain.character.Character;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CharacterRepositoryTest {

    @Autowired
    private CharacterRepository characterRepository;

    public static String readFileFromResources(String filename) throws URISyntaxException, IOException {
        URL resource = CharacterRepositoryTest.class.getClassLoader().getResource(filename);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        return new String(bytes);
    }

    @Test
    public void importCharacters() {
        String jsonCharacterList = null;
        try {
            jsonCharacterList = readFileFromResources("characters.json");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter();
        List<Character> characters = new ArrayList<>();

        try {
            characters = objectMapper.readValue(jsonCharacterList, objectMapper.getTypeFactory().constructCollectionType(List.class, Character.class));
            for (Character character : characters) {
                characterRepository.save(character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
