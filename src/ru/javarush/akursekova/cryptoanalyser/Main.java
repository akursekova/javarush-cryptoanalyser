package ru.javarush.akursekova.cryptoanalyser;
/*
encrypt -input=Test\test.txt -output=Test\testoutput.txt -shift=15
encrypt -input=Test\input1.txt -output=Test\result1.txt -shift=15
decryptWithShift -input=Test\input2.txt -output=Test\result2.txt -shift=15
decryptWithShift -input=Test\testoutput.txt -output=Test\testoutput-dec.txt -shift=15
decryptWithBruteForce -input=Test\input3.txt -output=Test\result3.txt -textToAnalise=Test\toAnalise.txt
decryptWithStatAnalysis -input=Test\input4.txt -output=Test\result4.txt -textToAnalise=Test\toAnalise.txt
decryptWithStatAnalysis -input=TEST\Manualtext\input4.txt -output=TEST\Manualtext\result4.txt -textToAnalise=TEST\Manualtext\textToAnalyse.txt
 */


import ru.javarush.akursekova.cryptoanalyser.InputDataProcessor.InputDataParser;

import static ru.javarush.akursekova.cryptoanalyser.InputDataProcessor.InputDataValidator.validateInputData;
import static ru.javarush.akursekova.cryptoanalyser.InputDataProcessor.OperationProcessor.processOperation;

public class Main {

    public static void main(String[] args) {
        validateInputData(args);
        processOperation(InputDataParser.parser(args));
    }
}
