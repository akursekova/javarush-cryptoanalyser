package ru.javarush.akursekova.cryptoanalyser;

import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataParser;
import static ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataValidator.validateInputData;
import static ru.javarush.akursekova.cryptoanalyser.input_data_processor.OperationProcessor.processOperation;

public class Main {
    public static void main(String[] args) {
        validateInputData(args);
        processOperation(InputDataParser.parser(args));
    }
}
