package ru.javarush.akursekova.cryptoanalyser.EncryptShift;

import ru.javarush.akursekova.cryptoanalyser.Alphabet.Alphabet;
import ru.javarush.akursekova.cryptoanalyser.InputDataProcessor.InputDataParser;
import java.io.*;


public class TextEncryptor {
    public static void encryptText(InputDataParser inputDataParser){

        String inputPath = inputDataParser.getInput();
        String outputPath = inputDataParser.getOutput();
        int shift = inputDataParser.getShift();


        try(FileReader input = new FileReader(inputPath);
            FileWriter output = new FileWriter(outputPath)
        ) {

            int readBytes;

            while ((readBytes = input.read()) != -1){
                char charBeforeEncrypt = (char) readBytes;
                if (Character.isUpperCase(charBeforeEncrypt)){
                        charBeforeEncrypt = Character.toLowerCase(charBeforeEncrypt);
                    }

                    int indexCharToEncrypt = Alphabet.getInstance().indexOf(charBeforeEncrypt);
                    if (indexCharToEncrypt == -1){
                        output.write(charBeforeEncrypt);
                        continue;
                    }
                    int indexWithShift = indexCharToEncrypt + shift;
                    if (indexWithShift > Alphabet.getInstance().size() - 1){
                        indexWithShift = indexWithShift - (Alphabet.getInstance().size() - 1) - 1;
                    }

                    char charAfterEncrypt = Alphabet.getInstance().get(indexWithShift);
                    output.write(charAfterEncrypt);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("There was a problem while trying to access a file which is not available.", e);
        } catch (IOException e) {
            throw new RuntimeException("There was a problem while working with Input/Output operations", e);
        }
    }
}
