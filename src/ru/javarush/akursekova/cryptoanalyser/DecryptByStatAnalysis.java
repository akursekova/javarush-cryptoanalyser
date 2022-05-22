package ru.javarush.akursekova.cryptoanalyser;

import java.nio.file.Path;
import java.util.Map;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class DecryptByStatAnalysis {

    public static void decryptByStat(Path textToDecrypt, Path textToAnalyse, Path decryptedText){
        Map<Character,Integer> exampleTextCharStat = CharacterStatistics.generateCharStats(textToAnalyse);
//        System.out.println(exampleTextCharStat.toString());
        Map<Character,Integer> decryptedTextCharStat = CharacterStatistics.generateCharStats(textToDecrypt);
//        System.out.println(decryptedTextCharStat.toString());

        char popularCharInExampleText = PopularCharFinder.findPopularChar(exampleTextCharStat);
        char popularCharInDecryptedText = PopularCharFinder.findPopularChar(decryptedTextCharStat);

//        System.out.println("popular char in example text = " + popularCharInExampleText);
//        System.out.println("popular char in decrypted text = " + popularCharInDecryptedText);

        int shift = ALPHABET.size()  - ALPHABET.indexOf(popularCharInExampleText) + ALPHABET.indexOf(popularCharInDecryptedText);
//        System.out.println("shift = " + shift);

        DecryptByShift.decryptText(textToDecrypt, decryptedText, shift);
    }
}
