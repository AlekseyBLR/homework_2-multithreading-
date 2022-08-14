package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.LetterCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class LineLetterCounter implements LetterCounter, Callable {
    private String line;

    public LineLetterCounter(String line) {
        this.line = line;
    }


    @Override
    public Map<Character, Long> call() throws Exception {
        return count(line);
    }

    @Override
    public Map<Character, Long> count(String input) {
        var listChars = input.chars().mapToObj(e->(char)e).collect(Collectors.toList());
        Map<Character, Long> countChar = listChars.stream().collect(HashMap::new, (m, c) -> {
            if (m.containsKey(c)) {
                m.put(c, m.get(c) + 1L);
            } else {
                m.put(c, 1L);
            }
        }, HashMap::putAll);
        return countChar;
    }
}
