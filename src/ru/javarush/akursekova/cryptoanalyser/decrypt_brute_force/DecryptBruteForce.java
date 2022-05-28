package ru.javarush.akursekova.cryptoanalyser.decrypt_brute_force;

import ru.javarush.akursekova.cryptoanalyser.decrypt_shift.DecryptShift;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static ru.javarush.akursekova.cryptoanalyser.Main.ALPHABET;

public class DecryptBruteForce {
    public static void decryptByBruteForce(InputData inputDataParser){

        String textToAnalyse = inputDataParser.getExampleText();
        String outputText = inputDataParser.getOutput();

        Set<String> exampleTextDictionary = DictionaryGenerator.generateDictionary(textToAnalyse);
        Set<String> decryptedTextDictionary;

        List<Integer> numOfWordsInBothSets = new ArrayList<>();

        int wordsInBothSets = 0;
        int maxWordsInBothSets = Integer.MIN_VALUE;


        for (int i = 1; i < ALPHABET.size(); i++) {
            int shiftToTry = i;

            inputDataParser.setShift(shiftToTry);
            DecryptShift.decryptText(inputDataParser);

            decryptedTextDictionary = DictionaryGenerator.generateDictionary(outputText);

            Iterator<String> it = decryptedTextDictionary.iterator();
            while (it.hasNext())
            {
                String wordToCheck = it.next();
                if (exampleTextDictionary.contains(wordToCheck)){
                    wordsInBothSets++;
                }
            }
            numOfWordsInBothSets.add(shiftToTry-1, wordsInBothSets);

            if (wordsInBothSets > maxWordsInBothSets){
                maxWordsInBothSets = wordsInBothSets;
            }
            wordsInBothSets = 0;
        }


        int correctShift = numOfWordsInBothSets.indexOf(maxWordsInBothSets) + 1;
        inputDataParser.setShift(correctShift);
        DecryptShift.decryptText(inputDataParser);
    }
}
