package com.daniilyurov.training.compressor;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The class that can read the compressed file (using {@code TextCompressor}, expand the
 * text using Huffman Coding, and return the text as String.
 */
public class TextExpander {

    private byte[] bytes;
    private Dictionary dictionary;
    private StringBuilder bld = new StringBuilder();
    private List<Boolean> codePointBits;

    /**
     * Reads the compressed file, expands it and
     * returns a String with the uncompressed text.
     */
    public String expand(File compressedFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInput = new FileInputStream(compressedFile);
        ZipInputStream zipInput = new ZipInputStream(fileInput);

        ZipEntry entry;

        while ((entry = zipInput.getNextEntry()) != null) {
            switch (entry.getName()) {
                case "text_data":
                    readBytes(zipInput);
                    break;
                case "dictionary":
                    readDictionary(zipInput);
                    break;
                default:
                    throw new RuntimeException("Wrong format!");
            }
        }
        zipInput.close();
        fileInput.close();
        return decompose();
    }

    private void readBytes(InputStream textInputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = textInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        this.bytes = out.toByteArray();
    }

    private void readDictionary(InputStream dictionaryInputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(dictionaryInputStream);
        this.dictionary = (Dictionary) is.readObject();
    }

    String decompose() {
        codePointBits = new LinkedList<>();

        for (int n = 0; n < bytes.length - 1; n++) {
            dec(bytes[n], 8);
        }
        dec(bytes[bytes.length - 1], dictionary.getBitsInLastByte());
        return bld.toString();
    }

    private void dec(byte b, int max) {
        for (int i = 0; i < max; i++) {
            byte mask = (byte)(1 << i);
            boolean bl = (b & mask) == mask;
            codePointBits.add(bl);
            Integer c;
            if ((c = dictionary.find(codePointBits)) != null) {
                codePointBits = new LinkedList<>();
                bld.appendCodePoint(c);
            }
        }
    }

}
