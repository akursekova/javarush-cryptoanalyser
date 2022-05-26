package ru.javarush.akursekova.cryptoanalyser.decrypt_shift;

import ru.javarush.akursekova.cryptoanalyser.alphabet.Alphabet;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DecryptShift {
    public static void decryptText(InputDataParser inputDataParser) {

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

                int indCharToDecrypt = Alphabet.getInstance().indexOf(charBeforeDecrypt);
                if (indCharToDecrypt == -1) {
                    output.write(charBeforeDecrypt);
                    continue;
                }
                int indWithShift = indCharToDecrypt - shift;
                if (indWithShift < 0) {
                    indWithShift = (Alphabet.getInstance().size() - 1) - (shift - indCharToDecrypt) + 1;
                }
                char charAfterDecrypt = Alphabet.getInstance().get(indWithShift);

                output.write(charAfterDecrypt);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("There was a problem while trying to access a file which is not available.", e);
        } catch (IOException e) {
            throw new RuntimeException("There was a problem while working with Input/Output operations", e);
        }
    }
}
