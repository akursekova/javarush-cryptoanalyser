package ru.javarush.akursekova.cryptoanalyser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;


//-inputpath=C:\Users\Image1\Desktop\ALINA\TEST\file.txt -outputpath=C:\Users\Image1\Desktop\ALINA\TEST\result.txt -shift=20

public class Main {

    private static final List<Character> ALPHABET = Arrays.asList('a', 'b', 'c',
            'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',  'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '.', ',', '\'', ':', '!', '?', ' ');

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));

//        final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в',
//                'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
//                'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»',
//                ':', '!', '?', ' ');

//        final List<Character> ALPHABET = Arrays.asList('a', 'b', 'c',
//                'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'o', 'p', 'q', 'r', 's', 't',
//                'u', 'v', 'w', 'x', 'y', 'z', '.', ',', '\'', ':', '!', '?', ' ');

        if (args.length != 3 || !args[0].startsWith("-inputpath")
               || !args[1].startsWith("-outputpath") || !args[2].startsWith("-shift")){
           System.err.println("Some of your parameters is missing! " +
                   "\nYou must specify parameters '-inputpath' and '-outputpath' with path " +
                   "and parameter '-shift' with rotation.");
           System.exit(1);
       }

        String[] inputKeyAndValue = args[0].split("=");
        String[] outputKeyAndValue = args[1].split("=");
        String[] shiftKeyAndValue = args[2].split("=");

       if (inputKeyAndValue.length != 2){
           System.out.println("You must specify parameter '-inputpath' with next format: -inputpath={value}");
           System.exit(2);
       }
        if (outputKeyAndValue.length != 2){
            System.out.println("You must specify parameter '-outputpath' with next format: -outputpath={value}");
            System.exit(2);
        }
        if (shiftKeyAndValue.length != 2){
            System.out.println("You must specify parameter '-shift' with next format: -shift={value}");
            System.exit(2);
        }


        //INPUT
        String inputValue = inputKeyAndValue[1];
        Path inputPath = null;

        try{
            inputPath = Path.of(inputValue);
        } catch (InvalidPathException ex){
            System.err.println("Path is invalid: " + inputValue);
            System.err.println("Error details: " + ex.getMessage());
            System.exit(3);
        }

        if (Files.notExists(inputPath)) {
            System.err.println("A file in the path " + inputValue + " does not exist." +
                    "\nYou must specify a path to existing file.");
            System.exit(4);
        }

        if (!inputValue.endsWith(".txt")){
            System.err.println("File with incorrect format specified in '-inputpath': " + inputValue
                    + "\nYou must specify a path to the file with .txt format.");
            System.exit(5);
        }

        try {
            if (Files.size(inputPath) == 0){
                System.err.println("The size of the file by the path "
                        + inputValue + " is " + Files.size(inputPath) + " bytes. "
                        + "\nYou must specify a path to the file which contains a text.");
                System.exit(6);
            }
        } catch (IOException e) {
            System.err.println("There was a problem while processing size of the file provided in '-input'");
            System.exit(7);
        }


        //OUTPUT
        String outputValue = outputKeyAndValue[1];
        Path outputPath = null;

        try{
            outputPath = Path.of(outputValue);
        } catch (InvalidPathException ex){
            System.err.println("Path is invalid: " + outputValue);
            System.err.println("Error details: " + ex.getMessage());
        }

        if (Files.notExists(outputPath)) {
            System.err.println("A file in the path " + outputValue + " does not exist." +
                    "\nYou must specify a path to existing file.");
            System.exit(8);
        }

        if (!outputValue.endsWith(".txt")){
            System.err.println("File with incorrect format specified in '-outputpath': " + outputValue +
                    "\nYou must specify a path to the file with .txt format.");
            System.exit(9);
        }

        //INPUT+OUTPUT
        try {
            if (Files.isSameFile(inputPath, outputPath)){
                System.err.println("Paths you provided in '-inputpath' and '-outputpath' are the same:"
                        + "\n'-inputpath' = " + inputPath + "\n'-outputpath' = " + outputValue
                        + "\n\nPlease, specify different paths.");
                System.exit(10);
            }
        } catch (IOException e) {
            System.err.println("There was a problem while comparing paths provided in 'inputpath' and '-outputpath'");
            System.exit(11);
        }

        //SHIFT
        String shiftValue = shiftKeyAndValue[1];

        try{
            Integer.parseInt(shiftValue);
        } catch (NumberFormatException ex){
            System.err.println("Shift is invalid: " + shiftValue + ". Please, provide integer value.");
            System.err.println("Error details: " + ex.getMessage());
            System.exit(12);
        }

        int shift = Integer.parseInt(shiftValue);
        int shiftMax = ALPHABET.size()-1;
        if (shift == 0 || shift == ALPHABET.size()){
            System.err.println("Shift is invalid: " + shiftValue
                    + ". \nThe text will not be encrypted if the shift equals to 0 or " + ALPHABET.size() + "."
                    + "\nPlease, provide shift value which will be in the range of [1, " + shiftMax + "].");
            System.exit(13);
        }
        //TODO work with not absolute path!

        //encryptFile(inputPath, outputPath, shift);

        //TODO outputPath for descryptFile method was not added to command line args.To add and support all validations.
        decryptFile(inputPath, outputPath, shift);

    }

    public static void encryptFile(Path inPath, Path outPath, int shift){
        try(FileChannel reader = FileChannel.open(inPath);
            FileChannel writer = FileChannel.open(outPath, StandardOpenOption.WRITE)
        ){
            ByteBuffer buffer = ByteBuffer.allocate(64);
            int readBytes = reader.read(buffer);

            while (readBytes != -1){
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {

                    char charBeforeEncrypt = (char) buffer.get(i);
                    if (Character.isUpperCase(charBeforeEncrypt)){
                        charBeforeEncrypt = Character.toLowerCase(charBeforeEncrypt);
                    }

                    int indCharToEncrypt = ALPHABET.indexOf(charBeforeEncrypt);
                    if (indCharToEncrypt == -1){
                        continue;
                    }
                    int indWithShift = indCharToEncrypt + shift;
                    if (indWithShift > ALPHABET.size() - 1){
                        indWithShift = indWithShift - (ALPHABET.size() - 1) - 1;
                    }

                    char charAfterEncrypt = ALPHABET.get(indWithShift);
                    buffer.put(i, (byte) charAfterEncrypt);


//                    System.out.println("----------------------------------------");
//                    System.out.println("charBeforeEncrypt = "+charBeforeEncrypt);
//                    System.out.println("indCharToEncrypt = "+indCharToEncrypt);
//                    System.out.println("indWithShift = "+ indWithShift);
//                    System.out.println("charAfterEncrypt = " + charAfterEncrypt);
//                    System.out.println("char after encryption taken from buffer= "+ (char) buffer.get(i));
//                    System.out.println("----------------------------------------");
                }
                writer.write(buffer);
                buffer.clear();
                readBytes = reader.read(buffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void decryptFile(Path inPath, Path outPath, int shift){
        try(FileChannel reader = FileChannel.open(inPath);
            FileChannel writer = FileChannel.open(outPath, StandardOpenOption.WRITE)
        ){
            ByteBuffer buffer = ByteBuffer.allocate(64);
            int readBytes = reader.read(buffer);

            while (readBytes != -1){
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {

                    char charBeforeDecrypt = (char) buffer.get(i);
                    if (Character.isUpperCase(charBeforeDecrypt)){
                        charBeforeDecrypt = Character.toLowerCase(charBeforeDecrypt);
                    }

                    int indCharToDecrypt = ALPHABET.indexOf(charBeforeDecrypt);
                    if (indCharToDecrypt == -1){
                        continue;
                    }
                    int indWithShift = indCharToDecrypt - shift;
                    if (indWithShift < 0){
                        indWithShift = (ALPHABET.size() - 1) - (shift - indCharToDecrypt) + 1;
                    }

                    char charAfterDecrypt = ALPHABET.get(indWithShift);
                    buffer.put(i, (byte) charAfterDecrypt);


//                    System.out.println("----------------------------------------");
//                    System.out.println("charBeforeDecrypt = "+charBeforeDecrypt);
//                    System.out.println("indCharToDecrypt = "+indCharToDecrypt);
//                    System.out.println("indWithShift = "+ indWithShift);
//                    System.out.println("charAfterDecrypt = " + charAfterDecrypt);
//                    System.out.println("char after decryption taken from buffer= "+ (char) buffer.get(i));
//                    System.out.println("----------------------------------------");
                }
                writer.write(buffer);
                buffer.clear();
                readBytes = reader.read(buffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
