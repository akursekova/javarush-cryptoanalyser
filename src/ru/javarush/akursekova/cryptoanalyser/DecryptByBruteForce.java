package ru.javarush.akursekova.cryptoanalyser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class DecryptByBruteForce {
    public static void decryptByBruteForce(InputDataParser inputDataParser){

        Path textToAnalyse = inputDataParser.getExampleTextPath();
        Path outputText = inputDataParser.getOutputPath();

        Set<String> exampleTextDictionary = DictionaryGenerator.generateDictionary(textToAnalyse);
        Set<String> decryptedTextDictionary;

        List<Integer> numOfWordsInBothSets = new ArrayList<>();

        int wordsInBothSets = 0;
        int maxWordsInBothSets = Integer.MIN_VALUE;

        for (int i = 1; i < ALPHABET.size(); i++) {
            int shiftToTry = i;

            inputDataParser.setShift(shiftToTry);
            DecryptByShift.decryptText(inputDataParser);

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
        DecryptByShift.decryptText(inputDataParser);


    }
}
