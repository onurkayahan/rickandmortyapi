package com.onurkayahan.rickandmorty.domain.thread;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Accessors(chain = true)
@Getter
@Setter
public class CounterThread extends Thread {
    public List<String> characterNames;
    public static int poolCount = 0;
    public int threadCount = 0;

    @Override
    public void run() {
        if (characterNames != null) {
            characterNames.forEach(characterName -> {
                poolCount += characterName.length();
                threadCount += characterName.length();
            });
            System.out.println(Thread.currentThread().getName() + " - charCount : " + threadCount);
        }
    }
}
