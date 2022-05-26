package ru.javarush.akursekova.cryptoanalyser.InputDataProcessor;

import ru.javarush.akursekova.cryptoanalyser.DecryptBruteForce.DecryptBruteForce;
import ru.javarush.akursekova.cryptoanalyser.DecryptShift.DecryptShift;
import ru.javarush.akursekova.cryptoanalyser.DecryptStatisticsAnalysis.DecryptStatisticsAnalysis;
import ru.javarush.akursekova.cryptoanalyser.EncryptShift.TextEncryptor;

import java.nio.file.Path;

public class OperationProcessor {
    public static void processOperation(InputDataParser inputDataParser){
        if ("encrypt".equals(inputDataParser.getOperation())){
            TextEncryptor.encryptText(inputDataParser);
            System.out.println("Encryption completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
        if ("decryptWithShift".equals(inputDataParser.getOperation())){
            DecryptShift.decryptText(inputDataParser);
            System.out.println("Decryption completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
        if ("decryptWithBruteForce".equals(inputDataParser.getOperation())){
            DecryptBruteForce.decryptByBruteForce(inputDataParser);
            System.out.println("Decryption by Brute Force method completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
        if ("decryptWithStatAnalysis".equals(inputDataParser.getOperation())){
           DecryptStatisticsAnalysis.decryptByStat(inputDataParser);
            System.out.println("Decryption by Statistics Analysis method completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
    }
}
