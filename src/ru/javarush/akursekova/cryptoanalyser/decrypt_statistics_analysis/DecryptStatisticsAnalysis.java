package ru.javarush.akursekova.cryptoanalyser.decrypt_statistics_analysis;

import ru.javarush.akursekova.cryptoanalyser.alphabet.Alphabet;
import ru.javarush.akursekova.cryptoanalyser.decrypt_shift.DecryptShift;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataParser;
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
