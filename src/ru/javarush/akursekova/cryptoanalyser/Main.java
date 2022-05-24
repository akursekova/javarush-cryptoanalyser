package ru.javarush.akursekova.cryptoanalyser;
/*
encrypt -input=Test\input1.txt -output=Test\result1.txt -shift=15
decryptWithShift -input=Test\input2.txt -output=Test\result2.txt
decryptWithBruteForce -input=Test\input3.txt -output=Test\result3.txt -textToAnalise=Test\toAnalise.txt
decryptWithStatAnalysis -input=Test\input4.txt -output=Test\result4.txt -textToAnalise=toAnalise.txt
 */


import java.nio.file.Path;

import static ru.javarush.akursekova.cryptoanalyser.InputDataValidator.validateInputData;

public class Main {

    public static void main(String[] args) {

        validateInputData(args);

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
        } else {
            textExamplePath = Path.of(textExample[1]);
        }


        if ("encrypt".equals(args[0])){
            TextEncryptor.encryptText(inputPath, outputPath, shift);
        }
        if ("decryptWithShift".equals(args[0])){
            DecryptByShift.decryptText(inputPath, outputPath, shift);
        }
        if ("decryptWithBruteForce".equals(args[0])){
            DecryptByBruteForce.decryptByBruteForce(inputPath, outputPath, textExamplePath);
        }
        if ("decryptWithStatAnalysis".equals(args[0])){
            DecryptByStatAnalysis.decryptByStat(inputPath, outputPath, textExamplePath);
        }






//                && !"decryptWithShift".equals(args[0])
//                && !"decryptWithBruteForce".equals(args[0])
//                && !"decryptWithStatAnalysis".equals(args[0])
//        )

        TextEncryptor.encryptText(inputPath, outputPath, shift);
        DecryptByShift.decryptText(inputPath, outputPath, shift);





        DecryptByBruteForce.decryptByBruteForce(textToDecrypt, textToAnalise, decryptedText);
        DecryptByStatAnalysis.decryptByStat(textToDecrypt, textToAnalise, decryptedText);

    }
}
