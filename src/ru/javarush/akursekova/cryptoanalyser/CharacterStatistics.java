package ru.javarush.akursekova.cryptoanalyser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class CharacterStatistics {
    public static Map<Character, Integer> generateCharStats(Path textToAnalyse){

        Map<Character, Integer> charStatsMap = new HashMap<>();
        int frequencyInPercent;

        try(FileChannel fileChannel = FileChannel.open(textToAnalyse)){
            ByteBuffer buffer = ByteBuffer.allocate(1000);

            fileChannel.read(buffer);
            buffer.flip();

            for (int i = 0; i < buffer.limit(); i++) {
                char charToAnalyse = (char) buffer.get(i);

                if (Character.isUpperCase(charToAnalyse)){
                    charToAnalyse = Character.toLowerCase(charToAnalyse);
                }

                if (!ALPHABET.contains(charToAnalyse)){
                    continue;
                }
                if (!charStatsMap.containsKey(charToAnalyse)){
                    charStatsMap.put(charToAnalyse, 1);
                } else {
                    int oldCharCount = charStatsMap.get(charToAnalyse);
                    int newChartCount = oldCharCount + 1;
                    charStatsMap.put(charToAnalyse, newChartCount);
                }
            }
            buffer.clear();
        } catch (IOException e) {
            System.err.println("There was a problem while working with Input and Output operations");
            System.err.println("Error details: " + e.getMessage());
            System.exit(3);
        }

        for(Map.Entry<Character, Integer> entry: charStatsMap.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();

            frequencyInPercent = (value*100)/1000;

            charStatsMap.put(key,frequencyInPercent);
        }
        return charStatsMap;
    }
}
