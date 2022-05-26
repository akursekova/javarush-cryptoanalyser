package ru.javarush.akursekova.cryptoanalyser.DecryptBruteForce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DictionaryGenerator {
    public static Set<String> generateDictionary(String textToAnalysePath){

        Set<String> wordsDictionary = new HashSet<>();

        try(FileReader fileReader = new FileReader(textToAnalysePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {

            while (bufferedReader.ready()){
                String stringToAnalyze = bufferedReader.readLine();
                if (stringToAnalyze.equals("")){
                    continue;
                }
                String[] lineToStringArray = stringToAnalyze.split("[^A-Za-zА-Яа-я]+");

                for (int i = 0; i < lineToStringArray.length; i++) {
                    String wordToAnalyze  = lineToStringArray[i].toLowerCase();
                    if (wordToAnalyze.equals("")){
                        continue;
                    }
                    if (!wordsDictionary.contains(wordToAnalyze)){
                        wordsDictionary.add(wordToAnalyze);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("There was a problem while trying to access a file which is not available.", e);
        } catch (IOException e) {
            throw new RuntimeException("There was a problem while working with Input/Output operations", e);
        }

        return wordsDictionary;
    }
}
