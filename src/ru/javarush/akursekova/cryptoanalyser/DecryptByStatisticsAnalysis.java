package ru.javarush.akursekova.cryptoanalyser;

import java.nio.file.Path;
import java.util.Map;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class DecryptByStatisticsAnalysis {

    public static void decryptByStat(InputDataParser inputDataParser){

        Path inputText = inputDataParser.getInputPath();
        Path textToAnalyse = inputDataParser.getExampleTextPath();

        Map<Character,Integer> exampleTextCharStat = CharacterStatistics.generateCharStats(textToAnalyse);
        Map<Character,Integer> inputTextCharStat = CharacterStatistics.generateCharStats(inputText);

        char popularCharExampleText = PopularCharFinder.findPopularChar(exampleTextCharStat);
        char popularCharInputText = PopularCharFinder.findPopularChar(inputTextCharStat);

        int indexPopCharExampleText = ALPHABET.indexOf(popularCharExampleText);
        int indexPopCharInputText = ALPHABET.indexOf(popularCharInputText);

        int shift = ALPHABET.size() - indexPopCharExampleText + indexPopCharInputText;

        inputDataParser.setShift(shift);
        DecryptByShift.decryptText(inputDataParser);
    }
}
