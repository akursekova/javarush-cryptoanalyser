package ru.javarush.akursekova.cryptoanalyser.DecryptStatisticsAnalysis;

import ru.javarush.akursekova.cryptoanalyser.Alphabet.Alphabet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class CharacterStatistics {
    public static Map<Character, Integer> generateCharStats(String textToAnalyse){

        Map<Character, Integer> charStatsMap = new HashMap<>();
        int totalBytesRead = 0;
        int frequencyInPercent;

        try(FileReader input = new FileReader(textToAnalyse)) {
            int readBytes;


            while ((readBytes = input.read()) != -1 && totalBytesRead != 1000){
                char charToAnalyse = (char) readBytes;

                if (Character.isUpperCase(charToAnalyse)){
                    charToAnalyse = Character.toLowerCase(charToAnalyse);
                }

                if (!Alphabet.getInstance().contains(charToAnalyse)){
                    continue;
                }
                if (!charStatsMap.containsKey(charToAnalyse)){
                    charStatsMap.put(charToAnalyse, 1);
                } else {
                    int oldCharCount = charStatsMap.get(charToAnalyse);
                    int newChartCount = oldCharCount + 1;
                    charStatsMap.put(charToAnalyse, newChartCount);
                }
                totalBytesRead++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("There was a problem while trying to access a file which is not available.", e);
        } catch (IOException e) {
            throw new RuntimeException("There was a problem while working with Input/Output operations", e);
        }

        for(Map.Entry<Character, Integer> entry: charStatsMap.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();

            frequencyInPercent = Math.round((value*100)/totalBytesRead);

            charStatsMap.put(key,frequencyInPercent);
        }
        return charStatsMap;
    }
}
