package ru.javarush.akursekova.cryptoanalyser.encrypt_shift;

import ru.javarush.akursekova.cryptoanalyser.exception.FileProcessingException;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputData;
import java.io.*;
import java.nio.charset.Charset;

import static ru.javarush.akursekova.cryptoanalyser.Main.ALPHABET;


public class TextEncryptor {
    public static void encryptText(InputData inputDataParser){

        String inputPath = inputDataParser.getInput();
        String outputPath = inputDataParser.getOutput();
        int shift = inputDataParser.getShift();


        try(FileReader input = new FileReader(inputPath, Charset.defaultCharset());
            FileWriter output = new FileWriter(outputPath, Charset.defaultCharset())
        ) {

            int readBytes;

            while ((readBytes = input.read()) != -1){
                char charBeforeEncrypt = (char) readBytes;
                if (Character.isUpperCase(charBeforeEncrypt)){
                        charBeforeEncrypt = Character.toLowerCase(charBeforeEncrypt);
                    }

                    int indexCharToEncrypt = ALPHABET.indexOf(charBeforeEncrypt);
                    if (indexCharToEncrypt == -1){
                        output.write(charBeforeEncrypt);
                        continue;
                    }
                    int indexWithShift = indexCharToEncrypt + shift;
                    if (indexWithShift > ALPHABET.size() - 1){
                        indexWithShift = indexWithShift - (ALPHABET.size() - 1) - 1;
                    }

                    char charAfterEncrypt = ALPHABET.get(indexWithShift);
                    output.write(charAfterEncrypt);
            }
        } catch (FileNotFoundException e) {
            throw new FileProcessingException("There was a problem while trying to access a file which is not available.", e);
        } catch (IOException e) {
            throw new FileProcessingException("There was a problem while working with Input/Output operations", e);
        }
    }
}
