package ru.javarush.akursekova.cryptoanalyser;

public class ProgramRunner {
    public static void runProgram(InputDataParser inputDataParser){
        if ("encrypt".equals(inputDataParser.getOperation())){
            TextEncryptor.encryptText(inputDataParser);
            System.out.println("Encryption completed successfully.\n" +
                    "Please check results here: " + inputDataParser.getOutputPath().toAbsolutePath());
        }
        if ("decryptWithShift".equals(inputDataParser.getOperation())){
            DecryptByShift.decryptText(inputDataParser);
            System.out.println("Decryption completed successfully.\n" +
                    "Please check results here: " + inputDataParser.getOutputPath().toAbsolutePath());
        }
        if ("decryptWithBruteForce".equals(inputDataParser.getOperation())){
            DecryptByBruteForce.decryptByBruteForce(inputDataParser);
            System.out.println("Decryption by Brute Force method completed successfully.\n" +
                    "Please check results here: " + inputDataParser.getOutputPath().toAbsolutePath());
        }
        if ("decryptWithStatAnalysis".equals(inputDataParser.getOperation())){
           DecryptByStatisticsAnalysis.decryptByStat(inputDataParser);
            System.out.println("Decryption by Statistics Analysis method completed successfully.\n" +
                    "Please check results here: " + inputDataParser.getOutputPath().toAbsolutePath());
        }
    }
}
