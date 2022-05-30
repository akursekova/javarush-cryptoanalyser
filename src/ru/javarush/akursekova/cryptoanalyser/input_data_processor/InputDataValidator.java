package ru.javarush.akursekova.cryptoanalyser.input_data_processor;

import ru.javarush.akursekova.cryptoanalyser.exception.FileProcessingException;
import ru.javarush.akursekova.cryptoanalyser.exception.ValidationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static ru.javarush.akursekova.cryptoanalyser.Main.*;

public class InputDataValidator{

    public enum Operation {
        encrypt,
        decryptWithShift,
        decryptWithBruteForce,
        decryptWithStatAnalysis
    }
    private static final int MAX_OF_ARGUMENTS = 4;
    private static final int MAX_SHIFT = ALPHABET.size()-1;
    private static final String EXPECTED_FILE_EXTENSION = ".txt";
    static final String ARGUMENT_SEPARATOR = "=";


    private static final String INVALID_OPERATION = "Provided operation is invalid: %s " +
            "\n\nPlease, specify one of the operations from the list below: \n" +
            "'encrypt' for text encryption\n" +
            "'decryptWithShift' for text decryption using a shift\n" +
            "'decryptWithBruteForce' for text decryption using Brute Force method\n" +
            "'decryptWithStatAnalysis' for text decryption using statistics analysis";
    private static final String MISSING_PARAMETER_PATH_SHIFT = "Some of your parameters is missing! " +
            "\nYou must specify parameters '-input' and '-output' with a path " +
            "and parameter '-shift' with some rotation.";
    private static final String MISSING_PARAMETER_PATHS = "Some of your parameters is missing! " +
            "\nYou must specify parameters '-input','-output' and '-textToAnalyse' with a path.";

    private static final String INVALID_PARAMETER_FORMAT = "You must specify parameter '%s' with next format: %s={value}";

    private static final String NON_INTEGER_SHIFT = "Shift is invalid: %s. \nPlease, provide integer value.";
    private static final String OUT_OF_RANGE_SHIFT = "Shift is invalid: %s. " +
            "\nPlease, provide shift value which will be in the range of [1, %d].";

    private static final String INVALID_PATH = "Path specified in '%s' is invalid: %s";
    private static final String PATH_IS_DIRECTORY = "Path specified in '%s': %s is directory! " +
            "\nYou must specify a file path.";
    private static final String FILE_DOES_NOT_EXIST = "File at the specified path '%s' does not exist. " +
            "\nPlease, specify a path to existing file in the '%s'.";
    private static final String PATH_IS_NOT_TXT = "File with incorrect format specified in '%s': %s. " +
            "\nYou must specify a path to the file with %s format.";
    private static final String FILE_IS_EMPTY = "The size of the file by the path %s is %d bytes. " +
            "\nYou must specify a path to the file which is not empty in '%s'.";
    private static final String PROBLEM_WITH_PROCESS_FILE = "There was a problem " +
            "while processing size of the file provided in '%s'.";

    private static final String SAME_PATHS = "Paths you provided in '%s' and '%s' are the same:"
            + "\n'%s' = %s \n'%s' = %s\n\nYou must specify different paths.";
    private static final String PROBLEM_TO_COMPARE_PATHS = "There was a problem " +
            "while comparing paths provided in '%s' and '%s'.";
    public static void validateInputData(String[] args){
        if (!Operation.encrypt.toString().equals(args[0])
                && !Operation.decryptWithShift.toString().equals(args[0])
                && !Operation.decryptWithBruteForce.toString().equals(args[0])
                && !Operation.decryptWithStatAnalysis.toString().equals(args[0])
        ){
            throw new ValidationException(String.format(INVALID_OPERATION, args[0]));
        }

        if ((Operation.encrypt.toString().equals(args[0]) || Operation.decryptWithShift.toString().equals(args[0]))
                && (MAX_OF_ARGUMENTS != 4 || !args[1].startsWith("-input")
                || !args[2].startsWith("-output") || !args[3].startsWith("-shift"))){
            throw new ValidationException(MISSING_PARAMETER_PATH_SHIFT);
        }

        if ((Operation.decryptWithBruteForce.toString().equals(args[0])
                || Operation.decryptWithStatAnalysis.toString().equals(args[0]))
                && (MAX_OF_ARGUMENTS != 4 || !args[1].startsWith("-input")
                || !args[2].startsWith("-output") || !args[3].startsWith("-textToAnalyse"))){
            throw new ValidationException(MISSING_PARAMETER_PATHS);
        }

        validateArgFormat(args[1]);
        validateArgFormat(args[2]);
        if (Operation.encrypt.toString().equals(args[0]) || Operation.decryptWithShift.toString().equals(args[0])){
            validateArgFormat(args[3]);
        } else {
            validateArgFormat(args[3]);
        }

        validatePath(args[1]);
        validatePath(args[2]);
        isSameFile(args[1], args[2]);
        if (Operation.encrypt.toString().equals(args[0]) || Operation.decryptWithShift.toString().equals(args[0])){
            validateShift(args[3]);
        } else {
            isSameFile(args[1], args[3]);
            isSameFile(args[2], args[3]);
            validatePath(args[3]);
        }
    }

    public static void validateArgFormat(String arg){
        String[] argKeyAndValue = arg.split(ARGUMENT_SEPARATOR);

        if (argKeyAndValue.length != 2){
            throw new ValidationException(String.format(INVALID_PARAMETER_FORMAT, argKeyAndValue[0], argKeyAndValue[0]));
        }
    }

    public static void validateShift(String arg){
        String[] shiftValue = arg.split(ARGUMENT_SEPARATOR);

        try{
            Integer.parseInt(shiftValue[1]);
        } catch (NumberFormatException ex){
            throw new ValidationException(String.format(NON_INTEGER_SHIFT, shiftValue[1]));
        }

        int shift = Integer.parseInt(shiftValue[1]);
        if (shift == 0 || shift >= ALPHABET.size()){
            throw new ValidationException(String.format(OUT_OF_RANGE_SHIFT, shiftValue[1], MAX_SHIFT));
        }
    }

    private static void validatePath(String arg){
        String[] pathKeyAndValue = arg.split(ARGUMENT_SEPARATOR);
        Path path;
        int sizeOfFile;

        try{
            path = Path.of(pathKeyAndValue[1]);
        } catch (InvalidPathException ex){
            throw new FileProcessingException(String.format(INVALID_PATH, pathKeyAndValue[0], pathKeyAndValue[1]), ex);
        }

        if (Files.isDirectory(path)) {
            throw new ValidationException(String.format(PATH_IS_DIRECTORY, pathKeyAndValue[0], pathKeyAndValue[1]));
        }

        if (Files.notExists(path)) {
            throw new ValidationException(String.format(FILE_DOES_NOT_EXIST, pathKeyAndValue[1], pathKeyAndValue[0]));
        }

        if (!arg.endsWith(EXPECTED_FILE_EXTENSION)){
            throw new ValidationException(String.format(PATH_IS_NOT_TXT,
                    pathKeyAndValue[0], pathKeyAndValue[1], EXPECTED_FILE_EXTENSION));
        }

        if ("-input".equals(pathKeyAndValue[0]) || "-textToAnalyse".equals(pathKeyAndValue[0])){
            try {
                sizeOfFile = (int) Files.size(path);
                if (sizeOfFile == 0){
                    throw new ValidationException(String.format(FILE_IS_EMPTY,
                            pathKeyAndValue[1], sizeOfFile, pathKeyAndValue[0]));
                }
            } catch (IOException e) {
                throw new FileProcessingException(String.format(PROBLEM_WITH_PROCESS_FILE, pathKeyAndValue[0]), e);
            }
        }
    }

    public static void isSameFile(String arg1, String arg2){
        String[] path1KeyAndValue = arg1.split(ARGUMENT_SEPARATOR);
        String[] path2KeyAndValue = arg2.split(ARGUMENT_SEPARATOR);
        Path path1 = Path.of(path1KeyAndValue[1]);
        Path path2 = Path.of(path2KeyAndValue[1]);

        try {
            if (Files.isSameFile(path1, path2)){
                throw new ValidationException(String.format(SAME_PATHS, path1KeyAndValue[0], path2KeyAndValue[0],
                        path1KeyAndValue[0], path1KeyAndValue[1], path2KeyAndValue[0], path2KeyAndValue[1]));
            }
        } catch (IOException e) {
            throw new FileProcessingException(String.format(PROBLEM_TO_COMPARE_PATHS,
                    path1KeyAndValue[0], path2KeyAndValue[0]), e);
        }
    }
}
