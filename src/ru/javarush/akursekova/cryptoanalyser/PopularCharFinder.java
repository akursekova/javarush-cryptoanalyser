package ru.javarush.akursekova.cryptoanalyser;

import java.util.Map;

public class PopularCharFinder {
    public static Character findPopularChar(Map<Character, Integer> map){
        char popularChar = 0;
        int maxFrequency = Integer.MIN_VALUE;

        for(Map.Entry<Character, Integer> entry: map.entrySet()) {
            // get key
            Character key = entry.getKey();
            // get value
            Integer value = entry.getValue();
            if (value > maxFrequency){
                maxFrequency = value;
                popularChar = key;
            }
        }
        return popularChar;
    }
}
