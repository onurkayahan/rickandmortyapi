package com.onurkayahan.rickandmorty.thread;

import com.onurkayahan.rickandmorty.domain.thread.CounterThread;
import com.onurkayahan.rickandmorty.service.CharacterService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class CharacterNameCounterExecuterService {
    private static final int MYTHREADS = 25;


    private final CharacterService characterService;

    public CharacterNameCounterExecuterService(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostConstruct
    public void execute() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        List<String> characterNames = characterService.getAllCharacterNamesWithoutPageable();
        List<List<String>> parts = chopped(characterNames,MYTHREADS-1);

        CounterThread counterThread = null;
        for (int i = 0; i < MYTHREADS; i++) {
            counterThread = new CounterThread()
                    .setCharacterNames(parts.get(i));
            executor.submit(counterThread);
        };

        executor.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Total - charCount" + " : " + counterThread.poolCount);
    }

    // chops a list into non-view sublists of length L
    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }
}
