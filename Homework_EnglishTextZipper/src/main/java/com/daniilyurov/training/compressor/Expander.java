package com.daniilyurov.training.compressor;

import java.io.*;
import java.util.*;

public class Expander {

    public String decompress(InputStream textInputStream, InputStream dictionaryInputStream)
            throws IOException, ClassNotFoundException {
        byte[] bytes = readBytes(textInputStream);
        Dictionary dictionary= readDictionary(dictionaryInputStream);
        textInputStream.close();
        dictionaryInputStream.close();

        Decoder decoder = new Decoder(dictionary, bytes);
        return decoder.decompose();
    }

    private byte[] readBytes(InputStream textInputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length = 0;
        while ((length = textInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        return out.toByteArray();
    }

    private Dictionary readDictionary(InputStream dictionaryInputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(dictionaryInputStream);
        return (Dictionary) is.readObject();
    }

    private static class Decoder {
        Dictionary dictionary;
        byte[] bytes;
        StringBuilder bld;

        List<Boolean> codePoint;

        Decoder(Dictionary dictionary, byte[] bytes) {
            this.dictionary = dictionary;
            this.bytes = bytes;
            this.bld = new StringBuilder();
        }

        String decompose() {
            codePoint = new LinkedList<>();
            for (byte b : bytes) {
                for (int i = 0; i < 8; i++) {
                    byte mask = (byte)(1 << i);
                    boolean bl = ((b & mask) == mask);
                    codePoint.add(bl);
                    Integer c = null;
                    if ((c = dictionary.find(codePoint)) != null) {
                        codePoint = new LinkedList<>();
                        bld.appendCodePoint(c);
                    }
                }

            }
            return bld.toString();
        }
    }
}
