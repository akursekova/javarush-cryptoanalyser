package ru.javarush.akursekova.cryptoanalyser.decrypt_brute_force;

import ru.javarush.akursekova.cryptoanalyser.exception.FileProcessingException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static ru.javarush.akursekova.cryptoanalyser.Main.TEXT_INTO_WORDS;

public class DictionaryGenerator {
    public static Set<String> generateDictionary(String textToAnalysePath){

        Set<String> wordsDictionary = new HashSet<>();

        try(FileReader fileReader = new FileReader(textToAnalysePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {

            while (bufferedReader.ready()){
                String stringToAnalyze = bufferedReader.readLine();
                if ("".equals(stringToAnalyze)){
                    continue;
                }
                String[] lineToStringArray = stringToAnalyze.split(TEXT_INTO_WORDS);

                for (int i = 0; i < lineToStringArray.length; i++) {
                    String wordToAnalyze  = lineToStringArray[i].toLowerCase();
                    if ("".equals(wordToAnalyze)){
                        continue;
                    }
                    if (!wordsDictionary.contains(wordToAnalyze)){
                        wordsDictionary.add(wordToAnalyze);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new FileProcessingException("There was a problem while trying to access a file which is not available.", e);
        } catch (IOException e) {
            throw new FileProcessingException("There was a problem while working with Input/Output operations", e);
        }

        return wordsDictionary;
    }
}
