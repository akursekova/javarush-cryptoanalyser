package ru.javarush.akursekova.cryptoanalyser.encrypt_shift;

import ru.javarush.akursekova.cryptoanalyser.alphabet.Alphabet;
import ru.javarush.akursekova.cryptoanalyser.exception.FileProcessingException;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataParser;
import java.io.*;
import java.nio.charset.Charset;


public class TextEncryptor {
    public static void encryptText(InputDataParser inputDataParser){

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
            throw new FileProcessingException("There was a problem while trying to access a file which is not available.", e);
        } catch (IOException e) {
            throw new FileProcessingException("There was a problem while working with Input/Output operations", e);
        }
    }
}
