package ru.javarush.akursekova.cryptoanalyser.decrypt_statistics_analysis;

import ru.javarush.akursekova.cryptoanalyser.decrypt_shift.DecryptShift;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputData;
import java.util.Map;

import static ru.javarush.akursekova.cryptoanalyser.Main.ALPHABET;

public class DecryptStatisticsAnalysis {

    public static void decryptByStat(InputData inputData){

        String inputText = inputData.getInput();
        String textToAnalyse = inputData.getExampleText();

        Map<Character,Integer> exampleTextCharStat = CharacterStatistics.generateCharStats(textToAnalyse);
        Map<Character,Integer> inputTextCharStat = CharacterStatistics.generateCharStats(inputText);

        char popularCharExampleText = PopularCharFinder.findPopularChar(exampleTextCharStat);
        char popularCharInputText = PopularCharFinder.findPopularChar(inputTextCharStat);

        int indexPopCharExampleText = ALPHABET.indexOf(popularCharExampleText);
        int indexPopCharInputText = ALPHABET.indexOf(popularCharInputText);

        int shift = ALPHABET.size() - indexPopCharExampleText + indexPopCharInputText;

        inputData.setShift(shift);
        DecryptShift.decryptText(inputData);
    }
}
