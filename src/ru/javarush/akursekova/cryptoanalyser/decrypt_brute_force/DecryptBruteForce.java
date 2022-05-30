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

        int maxWordsInBothSets;
        int shiftToTry, correctShift;


        for (int i = 1; i < ALPHABET.size(); i++) {
            shiftToTry = i;
            inputDataParser.setShift(shiftToTry);
            DecryptShift.decryptText(inputDataParser);
            decryptedTextDictionary = DictionaryGenerator.generateDictionary(outputText);
            numOfWordsInBothSets.add(shiftToTry-1,
                    countWordsInBothSets(exampleTextDictionary, decryptedTextDictionary));
        }

        maxWordsInBothSets = maxValue(numOfWordsInBothSets);

        correctShift = numOfWordsInBothSets.indexOf(maxWordsInBothSets) + 1;
        inputDataParser.setShift(correctShift);
        DecryptShift.decryptText(inputDataParser);
    }

    public static int countWordsInBothSets(Set<String> set1, Set<String> set2){
        int wordsInBothSets = 0;

        Iterator<String> it = set2.iterator();
        while (it.hasNext())
        {
            String wordToCheck = it.next();
            if (set1.contains(wordToCheck)){
                wordsInBothSets++;
            }
        }
        return wordsInBothSets;
    }

    public static int maxValue(List<Integer> list){
        int max = Integer.MIN_VALUE;
        int currentListValue;

        for (int i = 0; i < list.size(); i++) {
            currentListValue = list.get(i);
            if (currentListValue > max){
                max = currentListValue;
            }
        }
        return max;
    }

}
