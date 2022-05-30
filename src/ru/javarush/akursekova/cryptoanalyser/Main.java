package ru.javarush.akursekova.cryptoanalyser;

import ru.javarush.akursekova.cryptoanalyser.alphabet.Alphabet;
import ru.javarush.akursekova.cryptoanalyser.exception.FileProcessingException;
import ru.javarush.akursekova.cryptoanalyser.exception.ValidationException;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputData;

import java.util.List;

import static ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataValidator.validateInputData;
import static ru.javarush.akursekova.cryptoanalyser.input_data_processor.OperationProcessor.processOperation;

public class Main {

    public static final List<Character> ALPHABET = Alphabet.getInstance();
    public static final String ERROR_FILE_NOT_FOUND = "There was a problem while trying to access a file which is not available.";
    public static final String ERROR_INPUT_OUTPUT = "There was a problem while working with Input/Output operations";

    public static void main(String[] args) {
        try {
            validateInputData(args);
        } catch (ValidationException | FileProcessingException e){
            System.err.println(e.getMessage());
            return;
        }
        processOperation(InputData.parser(args));
    }
}
