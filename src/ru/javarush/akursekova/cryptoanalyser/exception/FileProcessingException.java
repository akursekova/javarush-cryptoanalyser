package ru.javarush.akursekova.cryptoanalyser.exception;

public class FileProcessingException extends RuntimeException{
    public FileProcessingException() {
    }

    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
