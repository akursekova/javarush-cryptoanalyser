package ru.javarush.akursekova.cryptoanalyser;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class TextEncryptor {
    public static void encryptText(InputDataParser inputDataParser){

        Path inputPath = inputDataParser.getInputPath();
        Path outputPath = inputDataParser.getOutputPath();
        int shift = inputDataParser.getShift();


        try(FileReader input = new FileReader(inputPath.toFile());
            FileWriter output = new FileWriter(outputPath.toFile())
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
            System.err.println("There was a problem while trying to access a file which is not available. ");
            System.err.println("Error details: " + e.getMessage());
            System.exit(3);
        } catch (IOException e) {
            System.err.println("There was a problem while working with Input and Output operations");
            System.err.println("Error details: " + e.getMessage());
            System.exit(3);
        }


//        Path inputPath = inputDataParser.getInputPath();
//        Path outputPath = inputDataParser.getOutputPath();
//        int shift = inputDataParser.getShift();
//
//        try(FileChannel reader = FileChannel.open(inputPath);
//            FileChannel writer = FileChannel.open(outputPath, StandardOpenOption.WRITE)
//        ){
//            ByteBuffer buffer = ByteBuffer.allocate(64);
//            int readBytes = reader.read(buffer);
//
//            while (readBytes != -1){
//                buffer.flip();
//                for (int i = 0; i < buffer.limit(); i++) {
//
//                    char charBeforeEncrypt = (char) buffer.get(i);
//                    if (Character.isUpperCase(charBeforeEncrypt)){
//                        charBeforeEncrypt = Character.toLowerCase(charBeforeEncrypt);
//                    }
//
//                    int indexCharToEncrypt = ALPHABET.indexOf(charBeforeEncrypt);
//                    if (indexCharToEncrypt == -1){
//                        continue;
//                    }
//                    int indexWithShift = indexCharToEncrypt + shift;
//                    if (indexWithShift > ALPHABET.size() - 1){
//                        indexWithShift = indexWithShift - (ALPHABET.size() - 1) - 1;
//                    }
//
//                    char charAfterEncrypt = ALPHABET.get(indexWithShift);
//                    buffer.put(i, (byte) charAfterEncrypt);
//                }
//                writer.write(buffer);
//                buffer.clear();
//                readBytes = reader.read(buffer);
//            }
//        } catch (IOException e) {
//            System.err.println("There was a problem while working with Input and Output operations");
//            System.err.println("Error details: " + e.getMessage());
//            System.exit(3);
//        }
    }
}
