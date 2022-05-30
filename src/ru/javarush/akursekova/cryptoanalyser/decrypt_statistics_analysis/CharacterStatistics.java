package ru.javarush.akursekova.cryptoanalyser.decrypt_statistics_analysis;

import ru.javarush.akursekova.cryptoanalyser.exception.FileProcessingException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.javarush.akursekova.cryptoanalyser.Main.*;

public class CharacterStatistics {

    public static final int MAX_OF_SYMBOLS = 1000;
    public static Map<Character, Integer> generateCharStats(String textToAnalyse){

        Map<Character, Integer> charStatsMap = new HashMap<>();
        int totalBytesRead = 0;
        int frequencyInPercent;

        try(FileReader input = new FileReader(textToAnalyse)) {
            int readBytes;

            while ((readBytes = input.read()) != -1 && totalBytesRead != MAX_OF_SYMBOLS){
                char charToAnalyse = (char) readBytes;

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
                totalBytesRead++;
            }
        } catch (FileNotFoundException e) {
            throw new FileProcessingException(ERROR_FILE_NOT_FOUND, e);
        } catch (IOException e) {
            throw new FileProcessingException(ERROR_INPUT_OUTPUT, e);
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
