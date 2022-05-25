package ru.javarush.akursekova.cryptoanalyser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class DictionaryGenerator {
    public static Set<String> generateDictionary(Path textToAnalyse){

        Set<String> wordsDictionary = new HashSet<>();

        try(FileReader fileReader = new FileReader(textToAnalyse.toFile());
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
            System.err.println("There was a problem while trying to access a file which is not available. ");
            System.err.println("Error details: " + e.getMessage());
            System.exit(3);
        } catch (IOException e) {
            System.err.println("There was a problem while working with Input and Output operations");
            System.err.println("Error details: " + e.getMessage());
            System.exit(3);
        }

        return wordsDictionary;
    }
}
