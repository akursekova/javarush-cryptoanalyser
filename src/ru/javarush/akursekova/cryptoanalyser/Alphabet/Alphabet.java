package ru.javarush.akursekova.cryptoanalyser.Alphabet;

import java.util.Arrays;
import java.util.List;

public class Alphabet {
    private static final List<Character> ALPHABET = Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n',  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н',
            'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            '.', ',', ':', ';', '!', '?', '"', '«', '»','+', '=', '@', '$', '%', '&', '*',
            '(', ')', '[', ']', '{', '}', '_', '-', '—', '–', '\\', '\'', '/', '№', '…', '#', '~', '¡', '¿', '^', ' '
    );

    public static List<Character> getInstance(){
        return ALPHABET;
    }
}
