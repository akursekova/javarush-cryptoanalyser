package ru.javarush.akursekova.cryptoanalyser.decrypt_shift;

import ru.javarush.akursekova.cryptoanalyser.exception.FileProcessingException;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputData;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static ru.javarush.akursekova.cryptoanalyser.Main.*;

public class DecryptShift {
    public static void decryptText(InputData inputDataParser) {

        String inputPath = inputDataParser.getInput();
        String outputPath = inputDataParser.getOutput();
        int shift = inputDataParser.getShift();


        try (FileReader input = new FileReader(inputPath);
             FileWriter output = new FileWriter(outputPath)
        ) {
            int readBytes;

            while ((readBytes = input.read()) != -1) {
                char charBeforeDecrypt = (char) readBytes;
                if (Character.isUpperCase(charBeforeDecrypt)) {
                    charBeforeDecrypt = Character.toLowerCase(charBeforeDecrypt);
                }

                int alphabetPosition = ALPHABET.indexOf(charBeforeDecrypt);
                if (alphabetPosition == -1) {
                    output.write(charBeforeDecrypt);
                    continue;
                }
                int newAlphabetPosition = alphabetPosition - shift;
                if (newAlphabetPosition < 0) {
                    newAlphabetPosition = (ALPHABET.size() - 1) - (shift - alphabetPosition) + 1;
                }
                char charAfterDecrypt = ALPHABET.get(newAlphabetPosition);

                output.write(charAfterDecrypt);
            }
        } catch (FileNotFoundException e) {
            throw new FileProcessingException(ERROR_FILE_NOT_FOUND, e);
        } catch (IOException e) {
            throw new FileProcessingException(ERROR_INPUT_OUTPUT, e);
        }
    }
}
