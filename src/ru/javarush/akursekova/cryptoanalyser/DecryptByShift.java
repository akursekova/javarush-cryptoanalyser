package ru.javarush.akursekova.cryptoanalyser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class DecryptByShift {
    public static void decryptText(InputDataParser inputDataParser) {

        Path inputPath = inputDataParser.getInputPath();
        Path outputPath = inputDataParser.getOutputPath();
        int shift = inputDataParser.getShift();


        try (FileReader input = new FileReader(inputPath.toFile());
             FileWriter output = new FileWriter(outputPath.toFile())
        ) {

            int readBytes;

            while ((readBytes = input.read()) != -1) {


                char charBeforeDecrypt = (char) readBytes;
                if (Character.isUpperCase(charBeforeDecrypt)) {
                    charBeforeDecrypt = Character.toLowerCase(charBeforeDecrypt);
                }

                int indCharToDecrypt = ALPHABET.indexOf(charBeforeDecrypt);
                if (indCharToDecrypt == -1) {
                    output.write(charBeforeDecrypt);
                    continue;
                }
                int indWithShift = indCharToDecrypt - shift;
                if (indWithShift < 0) {
                    indWithShift = (ALPHABET.size() - 1) - (shift - indCharToDecrypt) + 1;
                }
                char charAfterDecrypt = ALPHABET.get(indWithShift);

                output.write(charAfterDecrypt);
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
    }
}
