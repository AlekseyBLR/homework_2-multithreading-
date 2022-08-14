package ru.digitalhabbits.homework2.impl;

import java.io.File;

import static com.google.common.io.Resources.getResource;

public class Main {
    public static void main(String ...args){

        var url = getResource("test.txt");
        File file = new File(url.getPath());

        AsyncFileLetterCounter asyncFileLetterCounter = new AsyncFileLetterCounter();
        var map = asyncFileLetterCounter.count(file);
        System.out.println(map.toString());
    }
}
