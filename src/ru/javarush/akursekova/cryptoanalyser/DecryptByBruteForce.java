package ru.javarush.akursekova.cryptoanalyser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class DecryptByBruteForce {
    public static void decryptByBruteForce(Path textToDecrypt, Path textToAnalyse, Path decryptedText){


        //Map<String, Integer> wordStatFromExampleText = WordStatistics.wordCounter(textToAnalyse);
        Set<String> exampleTextDictionary = DictionaryGenerator.generateDictionary(textToAnalyse);
        //Map<String, Integer> wordStatFromDecryptedText;
        Set<String> decryptedTextDictionary;
        List<Integer> numOfWordsInBothSets = new ArrayList<>();
        int wordsInBothSets = 0;
        int maxWordsInBothSets = Integer.MIN_VALUE;

        //System.out.println(wordStatFromExampleText.toString());
//        System.out.println(exampleTextDictionary.toString());

        for (int i = 1; i < ALPHABET.size(); i++) {
            int shiftToTry = i;
//            System.out.println("----------------------------------------------");
//            System.out.println(shiftToTry);
            DecryptByShift.decryptText(textToDecrypt, decryptedText, shiftToTry);
            //wordStatFromDecryptedText = WordStatistics.wordCounter(decryptedText);
            decryptedTextDictionary = DictionaryGenerator.generateDictionary(decryptedText);
//            System.out.println(decryptedTextDictionary.toString());
//            System.out.println("----------------------------------------------");

//            for(Map.Entry<String, Integer> entry: wordStatFromDecryptedText.entrySet()) {
//                // get key
//                String key = entry.getKey();
//                // get value
////                Integer value = entry.getValue();
//                if (wordStatFromExampleText.containsKey(key)){
//                    wordsInBothSets++;
//                }
//            }
//            numOfWordsInBothSets.add(shiftToTry-1, wordsInBothSets);
//            wordsInBothSets = 0;

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

//        System.out.println(numOfWordsInBothSets.toString());
        int correctShift = numOfWordsInBothSets.indexOf(maxWordsInBothSets) + 1;

        DecryptByShift.decryptText(textToDecrypt, decryptedText, correctShift);

//        System.out.println("correct key = " + correctShift);

    }
}
