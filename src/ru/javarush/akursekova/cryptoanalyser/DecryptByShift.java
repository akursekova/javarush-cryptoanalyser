package ru.javarush.akursekova.cryptoanalyser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class DecryptByShift {
    public static void decryptText(Path textToDecrypt, Path decryptedText, int shift){
        try(FileChannel reader = FileChannel.open(textToDecrypt);
            FileChannel writer = FileChannel.open(decryptedText, StandardOpenOption.WRITE)
        ){
            ByteBuffer buffer = ByteBuffer.allocate(64);
            int readBytes = reader.read(buffer);

            while (readBytes != -1){
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {

                    char charBeforeDecrypt = (char) buffer.get(i);
                    if (Character.isUpperCase(charBeforeDecrypt)){
                        charBeforeDecrypt = Character.toLowerCase(charBeforeDecrypt);
                    }

                    int indCharToDecrypt = ALPHABET.indexOf(charBeforeDecrypt);
                    if (indCharToDecrypt == -1){
                        continue;
                    }
                    int indWithShift = indCharToDecrypt - shift;
                    if (indWithShift < 0){
                        indWithShift = (ALPHABET.size() - 1) - (shift - indCharToDecrypt) + 1;
                    }

                    char charAfterDecrypt = ALPHABET.get(indWithShift);
                    buffer.put(i, (byte) charAfterDecrypt);


//                    System.out.println("----------------------------------------");
//                    System.out.println("charBeforeDecrypt = "+charBeforeDecrypt);
//                    System.out.println("indCharToDecrypt = "+indCharToDecrypt);
//                    System.out.println("indWithShift = "+ indWithShift);
//                    System.out.println("charAfterDecrypt = " + charAfterDecrypt);
//                    System.out.println("char after decryption taken from buffer= "+ (char) buffer.get(i));
//                    System.out.println("----------------------------------------");
                }
                writer.write(buffer);
                buffer.clear();
                readBytes = reader.read(buffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
