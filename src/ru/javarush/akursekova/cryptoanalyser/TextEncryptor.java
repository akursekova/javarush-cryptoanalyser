package ru.javarush.akursekova.cryptoanalyser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static ru.javarush.akursekova.cryptoanalyser.Alphabet.ALPHABET;

public class TextEncryptor {
    public static void encryptText(Path textToEncrypt, Path encryptedText, int shift){
        try(FileChannel reader = FileChannel.open(textToEncrypt);
            FileChannel writer = FileChannel.open(encryptedText, StandardOpenOption.WRITE)
        ){
            ByteBuffer buffer = ByteBuffer.allocate(64);
            int readBytes = reader.read(buffer);

            while (readBytes != -1){
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {

                    char charBeforeEncrypt = (char) buffer.get(i);
                    if (Character.isUpperCase(charBeforeEncrypt)){
                        charBeforeEncrypt = Character.toLowerCase(charBeforeEncrypt);
                    }

                    int indCharToEncrypt = ALPHABET.indexOf(charBeforeEncrypt);
                    if (indCharToEncrypt == -1){
                        continue;
                    }
                    int indWithShift = indCharToEncrypt + shift;
                    if (indWithShift > ALPHABET.size() - 1){
                        indWithShift = indWithShift - (ALPHABET.size() - 1) - 1;
                    }

                    char charAfterEncrypt = ALPHABET.get(indWithShift);
                    buffer.put(i, (byte) charAfterEncrypt);


//                    System.out.println("----------------------------------------");
//                    System.out.println("charBeforeEncrypt = "+charBeforeEncrypt);
//                    System.out.println("indCharToEncrypt = "+indCharToEncrypt);
//                    System.out.println("indWithShift = "+ indWithShift);
//                    System.out.println("charAfterEncrypt = " + charAfterEncrypt);
//                    System.out.println("char after encryption taken from buffer= "+ (char) buffer.get(i));
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
