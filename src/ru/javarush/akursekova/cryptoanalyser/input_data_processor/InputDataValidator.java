package ru.javarush.akursekova.cryptoanalyser.input_data_processor;

import ru.javarush.akursekova.cryptoanalyser.alphabet.Alphabet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class InputDataValidator {
    public static void validateInputData(String[] args){
        if (!"encrypt".equals(args[0])
                && !"decryptWithShift".equals(args[0])
                && !"decryptWithBruteForce".equals(args[0])
                && !"decryptWithStatAnalysis".equals(args[0])
        ){
            System.err.println("Provided operation is invalid: " + args[0] + ".\n\n" +
                    "Please, specify one of the operations from the list below: \n" +
                    "'encrypt' for text encryption\n" +
                    "'decryptWithShift' for text decryption using a shift\n" +
                    "'decryptWithBruteForce' for text decryption using Brute Force method\n" +
                    "'decryptWithStatAnalysis' for text decryption using statistics analysis");
            System.exit(0);
        }

        if (("encrypt".equals(args[0]) || "decryptWithShift".equals(args[0]))
                && (args.length != 4 || !args[1].startsWith("-input")
                || !args[2].startsWith("-output") || !args[3].startsWith("-shift"))){
            System.err.println("Some of your parameters is missing! " +
                    "\nYou must specify parameters '-input' and '-output' with a path " +
                    "and parameter '-shift' with some rotation.");
            System.exit(1);
        }

        if (("decryptWithBruteForce".equals(args[0]) || "decryptWithStatAnalysis".equals(args[0]))
                && (args.length != 4 || !args[1].startsWith("-input")
                || !args[2].startsWith("-output") || !args[3].startsWith("-textToAnalyse"))){
            System.err.println("Some of your parameters is missing! " +
                    "\nYou must specify parameters '-input','-output' and '-textToAnalyse' with a path.");
            System.exit(2);
        }

        validateArgFormat(args[1]);
        validateArgFormat(args[2]);
        if ("encrypt".equals(args[0]) || "decryptWithShift".equals(args[0])){
            validateArgFormat(args[3]);
        } else {
            validateArgFormat(args[3]);
        }

        validatePath(args[1]);
        validatePath(args[2]);
        isSameFile(args[1], args[2]);
        if ("encrypt".equals(args[0]) || "decryptWithShift".equals(args[0])){
            validateShift(args[3]);
        } else {
            isSameFile(args[1], args[3]);
            isSameFile(args[2], args[3]);
            validatePath(args[3]);
        }
    }

    public static void validateArgFormat(String arg){
        String[] argKeyAndValue = arg.split("=");

        if (argKeyAndValue.length != 2){
            System.err.println("You must specify parameter '"
                    + argKeyAndValue[0]
                    + "' with next format: "
                    + argKeyAndValue[0]
                    + "={value}");
            System.exit(3);
        }

    }

    public static void validateShift(String arg){
        String[] shiftValue = arg.split("=");
        try{
            Integer.parseInt(shiftValue[1]);
        } catch (NumberFormatException ex){
            System.err.println("Shift is invalid: " + shiftValue[1] + ". Please, provide integer value.");
            System.err.println("Error details: " + ex.getMessage());
            System.exit(4);
        }

        int shift = Integer.parseInt(shiftValue[1]);
        final int MAX_SHIFT = Alphabet.getInstance().size()-1;
        if (shift == 0 || shift == Alphabet.getInstance().size()){
            System.err.println("Shift is invalid: " + shiftValue[1]
                    + ". \nThe text will not be encrypted/decrypted if the shift equals to 0 or " + Alphabet.getInstance().size()
                    + ". \nPlease, provide shift value which will be in the range of [1, " + MAX_SHIFT + "].");
            System.exit(5);
        }
    }

    public static void validatePath(String arg){
        String[] pathKeyAndValue = arg.split("=");
        Path path;

        try{
            path = Path.of(pathKeyAndValue[1]);
        } catch (InvalidPathException ex){
            throw new RuntimeException("Path specified in '" + pathKeyAndValue[0] + "' is invalid: " + pathKeyAndValue[1], ex);
        }

        if (Files.isDirectory(path)) {
            System.err.println("Path specified in '" + pathKeyAndValue[0] + "' is directory!" +
                    "\nYou must specify a file path.");
            System.exit(7);
        }

        if (Files.notExists(path)) {
            System.err.println("File at the specified path '" + path + "' does not exist." +
                    "\nPlease, specify a path to existing file in the '" + pathKeyAndValue[0] + "'." );
            System.exit(8);
        }


        if (!arg.endsWith(".txt")){
            System.err.println("File with incorrect format specified in '" + pathKeyAndValue[0] + "': " + path
                    + "\nYou must specify a path to the file with .txt format.");
            System.exit(9);
        }

        if ("-input".equals(pathKeyAndValue[0]) || "-textToAnalyse".equals(pathKeyAndValue[0])){
            try {
                if (Files.size(path) == 0){
                    System.err.println("The size of the file by the path "
                            + path + " is " + Files.size(path) + " bytes. "
                            + "\nYou must specify a path to the file which is not empty in '" + pathKeyAndValue[0] + "'.");
                    System.exit(10);
                }
            } catch (IOException e) {
                throw new RuntimeException("There was a problem while processing size of the file provided in '" + pathKeyAndValue[0] + "'.", e);
            }
        }
    }

    public static void isSameFile(String arg1, String arg2){
        String[] path1KeyAndValue = arg1.split("=");
        String[] path2KeyAndValue = arg2.split("=");
        Path path1 = Path.of(path1KeyAndValue[1]);
        Path path2 = Path.of(path2KeyAndValue[1]);

        try {
            if (Files.isSameFile(path1, path2)){
                System.err.println("Paths you provided in '" + path1KeyAndValue[0]
                        + "' and '" + path2KeyAndValue[0] + "' are the same:"
                        + "\n'" + path1KeyAndValue[0] + "' = " + path1 + "\n'" + path2KeyAndValue[0] +  "' = " + path2
                        + "\n\nYou must specify different paths.");
                System.exit(10);
            }
        } catch (IOException e) {
            throw new RuntimeException("There was a problem while comparing paths provided in '"
                    + path1KeyAndValue[0] + "' and '" + path2KeyAndValue[0] + "'. ", e);
        }
    }
}
