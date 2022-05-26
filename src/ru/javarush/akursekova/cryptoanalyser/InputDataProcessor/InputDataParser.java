package ru.javarush.akursekova.cryptoanalyser.InputDataProcessor;

public class InputDataParser {
    private final String operation;
    private final String input;
    private final String output;
    private final String exampleText;
    private int shift;


    public InputDataParser(String operation, String input, String output, int shift) {
        this.operation = operation;
        this.input = input;
        this.output = output;
        this.shift = shift;
        exampleText = null;
    }

    public InputDataParser(String operation, String input, String output, String exampleText) {
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

    public static InputDataParser parser(String[] args){
        InputDataParser inputDataParser;
        String operation = args[0];
        String[] input = args[1].split("=");
        String[] output = args[2].split("=");
        String[] shiftValue = null;
        String[] textExample = null;
        if ("encrypt".equals(args[0]) || "decryptWithShift".equals(args[0])){
            shiftValue = args[3].split("=");
        } else {
            textExample = args[3].split("=");
        }


        String inputPath = input[1];
        String outputPath = output[1];
        int shift;
        String textExamplePath;

        if ("encrypt".equals(args[0]) || "decryptWithShift".equals(args[0])){
            shift = Integer.parseInt(shiftValue[1]);
            inputDataParser = new InputDataParser(operation, inputPath, outputPath, shift);
        } else {
            textExamplePath = textExample[1];
            inputDataParser = new InputDataParser(operation, inputPath, outputPath, textExamplePath);
        }

        return inputDataParser;
    }
}
