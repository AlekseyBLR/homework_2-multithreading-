package ru.digitalhabbits.homework2.impl;

import lombok.SneakyThrows;
import ru.digitalhabbits.homework2.FileReader;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class SequentialFileReader implements FileReader {
    @SneakyThrows
    @Override
    public List<String> readLines(File file) {
        return Files.readAllLines(file.toPath());
    }
}
