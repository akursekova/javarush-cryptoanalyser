package ru.javarush.akursekova.cryptoanalyser.input_data_processor;

import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataValidator.Operation;
public class InputData {
    private final String operation;
    private final String input;
    private final String output;
    private final String exampleText;
    private int shift;


    public InputData(String operation, String input, String output, int shift) {
        this.operation = operation;
        this.input = input;
        this.output = output;
        this.shift = shift;
        exampleText = null;
    }

    public InputData(String operation, String input, String output, String exampleText) {
        this.operation = operation;
        this.input = input;
        this.output = output;
        this.exampleText = exampleText;
    }

    public String getOperation() {
        return operation;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getExampleText() {
        return exampleText;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public static InputData parser(String[] args){
        InputData inputDataParser;
        String operation = args[0];
        String[] input = args[1].split("=");
        String[] output = args[2].split("=");
        String[] shiftValue = null;
        String[] textExample = null;
        if (Operation.encrypt.toString().equals(args[0]) || Operation.decryptWithShift.toString().equals(args[0])){
            shiftValue = args[3].split("=");
        } else {
            textExample = args[3].split("=");
        }


        String inputPath = input[1];
        String outputPath = output[1];
        int shift;
        String textExamplePath;

        if (Operation.encrypt.toString().equals(args[0]) || Operation.decryptWithShift.toString().equals(args[0])){
            shift = Integer.parseInt(shiftValue[1]);
            inputDataParser = new InputData(operation, inputPath, outputPath, shift);
        } else {
            textExamplePath = textExample[1];
            inputDataParser = new InputData(operation, inputPath, outputPath, textExamplePath);
        }

        return inputDataParser;
    }
}
