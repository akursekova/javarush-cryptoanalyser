package ru.javarush.akursekova.cryptoanalyser;

import java.nio.file.Path;

public class InputDataParser {
    private final String operation;
    private final Path inputPath;
    private final Path outputPath;
    private  int shift;

    public void setShift(int shift) {
        this.shift = shift;
    }

    private final Path exampleTextPath;

    public Path getInputPath() {
        return inputPath;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    public String getOperation() {
        return operation;
    }

    public int getShift() {
        return shift;
    }

    public Path getExampleTextPath() {
        return exampleTextPath;
    }

    public InputDataParser(String operation, Path inputPath, Path outputPath, int shift) {
        this.operation = operation;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.shift = shift;
        exampleTextPath = null;
    }

    public InputDataParser(String operation, Path inputPath, Path outputPath, Path exampleTextPath) {
        this.operation = operation;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.exampleTextPath = exampleTextPath;
        shift = 0;
    }

    @Override
    public String toString() {
        return "InputDataParser{" +
                "inputPath=" + inputPath +
                ", outputPath=" + outputPath +
                ", shift=" + shift +
                ", exampleTextPath=" + exampleTextPath +
                '}';
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


        Path inputPath = Path.of(input[1]);
        Path outputPath = Path.of(output[1]);
        int shift = 0;
        Path textExamplePath = null;

        if ("encrypt".equals(args[0]) || "decryptWithShift".equals(args[0])){
            shift = Integer.parseInt(shiftValue[1]);
            inputDataParser = new InputDataParser(operation, inputPath, outputPath, shift);
        } else {
            textExamplePath = Path.of(textExample[1]);
            inputDataParser = new InputDataParser(operation, inputPath, outputPath, textExamplePath);
        }

        return inputDataParser;
    }
}
