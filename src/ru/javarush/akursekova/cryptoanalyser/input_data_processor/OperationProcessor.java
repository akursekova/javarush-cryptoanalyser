package ru.javarush.akursekova.cryptoanalyser.input_data_processor;

import ru.javarush.akursekova.cryptoanalyser.decrypt_brute_force.DecryptBruteForce;
import ru.javarush.akursekova.cryptoanalyser.decrypt_shift.DecryptShift;
import ru.javarush.akursekova.cryptoanalyser.decrypt_statistics_analysis.DecryptStatisticsAnalysis;
import ru.javarush.akursekova.cryptoanalyser.encrypt_shift.TextEncryptor;

import java.nio.file.Path;

import ru.javarush.akursekova.cryptoanalyser.input_data_processor.InputDataValidator.Operation;
public class OperationProcessor {
    public static void processOperation(InputData inputDataParser){
        if (Operation.encrypt.toString().equals(inputDataParser.getOperation())){
            TextEncryptor.encryptText(inputDataParser);
            System.out.println("Encryption completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
        if (Operation.decryptWithShift.toString().equals(inputDataParser.getOperation())){
            DecryptShift.decryptText(inputDataParser);
            System.out.println("Decryption completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
        if (Operation.decryptWithBruteForce.toString().equals(inputDataParser.getOperation())){
            DecryptBruteForce.decryptByBruteForce(inputDataParser);
            System.out.println("Decryption by Brute Force method completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
        if (Operation.decryptWithStatAnalysis.toString().equals(inputDataParser.getOperation())){
           DecryptStatisticsAnalysis.decryptByStat(inputDataParser);
            System.out.println("Decryption by Statistics Analysis method completed successfully.\n" +
                    "Please check results here: " + Path.of(inputDataParser.getOutput()).toAbsolutePath());
        }
    }
}
