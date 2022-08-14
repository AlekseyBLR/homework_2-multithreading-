package ru.digitalhabbits.homework2.impl;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import lombok.SneakyThrows;
import ru.digitalhabbits.homework2.FileLetterCounter;

public class AsyncFileLetterCounter implements FileLetterCounter {
    private static final int THREAD_COUNT = 5;

    @SneakyThrows
    @Override
    public Map<Character, Long> count(File input) {

        var reader = new SequentialFileReader();
        var listLines = reader.readLines(input);

        var listFutureCounter = new ArrayList<Future>();
        var executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        for (String line: listLines) {
            Callable<Map<Character, Long>> task = new LineLetterCounter(line);
            Future<Map<Character, Long>> future = executorService.submit(task);
            listFutureCounter.add(future);
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(50);
        }

        LetterCountMergeImpl letterCountMerge = new LetterCountMergeImpl(listFutureCounter);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(letterCountMerge);
    }
}