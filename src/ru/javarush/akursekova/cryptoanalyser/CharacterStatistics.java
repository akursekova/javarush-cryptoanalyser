package ru.javarush.akursekova.cryptoanalyser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class CharacterStatistics {
    public static Map<Character, Integer> generateCharStats(Path textToAnalyse){
        Map<Character, Integer> charStatsMap = new HashMap<>();
        int frequencyInPercent;

        try(FileChannel fileChannel = FileChannel.open(textToAnalyse)){
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            int readBytes = fileChannel.read(buffer);

                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {

                    char charToAnalize = (char) buffer.get(i);
                    if (Character.isUpperCase(charToAnalize)){
                        charToAnalize = Character.toLowerCase(charToAnalize);
                    }
                    if (!ALPHABET.contains(charToAnalize)){
                        continue;
                    }
                    if (!charStatsMap.containsKey(charToAnalize)){
                        charStatsMap.put(charToAnalize, 1);
                    } else {
                        int oldCharCount = charStatsMap.get(charToAnalize);
                        int newChartCount = oldCharCount + 1;
                        charStatsMap.put(charToAnalize, newChartCount);
                    }
                }
                buffer.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //convert to persentage
        for(Map.Entry<Character, Integer> entry: charStatsMap.entrySet()) {
                // get key
                Character key = entry.getKey();
                // get value
                Integer value = entry.getValue();
                frequencyInPercent = (value*100)/1000;
                charStatsMap.put(key,frequencyInPercent);
            }

        return charStatsMap;
    }
}
