package ru.javarush.akursekova.cryptoanalyser;

import ru.javarush.akursekova.cryptoanalyser.alphabet.Alphabet;
import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputData;

import java.util.List;

import static ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataValidator.validateInputData;
import static ru.javarush.akursekova.cryptoanalyser.input_data_processor.OperationProcessor.processOperation;

public class Main {
    public static final String TEXT_INTO_WORDS = "[^A-Za-zА-Яа-я]+";
    public static final List<Character> ALPHABET = Alphabet.getInstance();

    public static void main(String[] args) {
        validateInputData(args);
        processOperation(InputData.parser(args));
    }
}
