package ru.javarush.akursekova.cryptoanalyser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class DictionaryGenerator {
    public static /*Map<String, Integer>*/ Set<String> generateDictionary(Path textToAnalyse){

        //Map<String, Integer> wordCounterMap = new TreeMap<>();
        Set<String> wordsDictionary = new HashSet<>();

        try(FileReader fileReader = new FileReader(textToAnalyse.toFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {

            while (bufferedReader.ready()){
                String stringToAnalyze = bufferedReader.readLine();
                if (stringToAnalyze.equals("")){
                    continue;
                }
                String[] lineToStringArray = stringToAnalyze.split("\\W");

                for (int i = 0; i < lineToStringArray.length; i++) {
                    String wordToAnalyze  = lineToStringArray[i].toLowerCase();
                    if (wordToAnalyze.equals("")){
                        continue;
                    }

//                    if (!wordCounterMap.containsKey(wordToAnalyze)){
//                        wordCounterMap.put(wordToAnalyze,1);
//                    } else {
//                        int oldWordFrequency = wordCounterMap.get(wordToAnalyze);
//                        int newWordFrequency = oldWordFrequency + 1;
//                        wordCounterMap.put(wordToAnalyze, newWordFrequency);
//                    }

                    if (!wordsDictionary.contains(wordToAnalyze)){
                        wordsDictionary.add(wordToAnalyze);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //return wordCounterMap;
        return wordsDictionary;
    }
}
