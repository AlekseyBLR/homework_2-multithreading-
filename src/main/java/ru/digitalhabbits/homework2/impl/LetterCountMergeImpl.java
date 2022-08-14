package ru.digitalhabbits.homework2.impl;

import lombok.SneakyThrows;
import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LetterCountMergeImpl extends RecursiveTask<Map<Character, Long>> implements LetterCountMerger {

    private final List<Future> futureList;

    public LetterCountMergeImpl(List<Future> futureList) {
        this.futureList = futureList;
    }

    @Override
    public Map<Character, Long> merge(Map<Character, Long> first, Map<Character, Long> second) {
        Map<Character, Long> mergedMap = Stream.of(first, second)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Long::sum));
        return mergedMap;
    }

    @SneakyThrows
    @Override
    protected Map<Character, Long>  compute() {
        if(futureList.size() <= 2) {
            Map<Character, Long>  secondMergeMap = futureList.size() == 1 ? Collections.emptyMap() : (Map<Character, Long>) futureList.get(1).get();
            return merge((Map<Character, Long>)futureList.get(0).get() , secondMergeMap);
        }
        LetterCountMergeImpl firstHalfList = new LetterCountMergeImpl(futureList.subList(0, futureList.size()/2));
        LetterCountMergeImpl secondHalfList = new LetterCountMergeImpl(futureList.subList(futureList.size()/2, futureList.size()));
        firstHalfList.fork();
        secondHalfList.fork();
        return merge(firstHalfList.join() ,secondHalfList.join());
    }
}
