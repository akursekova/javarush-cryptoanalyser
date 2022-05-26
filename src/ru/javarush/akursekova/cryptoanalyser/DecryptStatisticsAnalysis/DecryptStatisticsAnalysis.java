package ru.javarush.akursekova.cryptoanalyser.DecryptStatisticsAnalysis;

import ru.javarush.akursekova.cryptoanalyser.Alphabet.Alphabet;
import ru.javarush.akursekova.cryptoanalyser.DecryptShift.DecryptShift;
import ru.javarush.akursekova.cryptoanalyser.InputDataProcessor.InputDataParser;

import java.util.Map;

public class DecryptStatisticsAnalysis {

    public static void decryptByStat(InputDataParser inputDataParser){

        String inputText = inputDataParser.getInput();
        String textToAnalyse = inputDataParser.getExampleText();

        Map<Character,Integer> exampleTextCharStat = CharacterStatistics.generateCharStats(textToAnalyse);
        Map<Character,Integer> inputTextCharStat = CharacterStatistics.generateCharStats(inputText);

        char popularCharExampleText = PopularCharFinder.findPopularChar(exampleTextCharStat);
        char popularCharInputText = PopularCharFinder.findPopularChar(inputTextCharStat);

        int indexPopCharExampleText = Alphabet.getInstance().indexOf(popularCharExampleText);
        int indexPopCharInputText = Alphabet.getInstance().indexOf(popularCharInputText);

        int shift = Alphabet.getInstance().size() - indexPopCharExampleText + indexPopCharInputText;

        inputDataParser.setShift(shift);
        DecryptShift.decryptText(inputDataParser);
    }
}
