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

    private byte[] bytesToDecode;
    private Dictionary dictionary;
    private StringBuilder decodedTextBuilder;
    private List<Boolean> unrecognizedCodePointBits;

    public TextExpander() {
        this.decodedTextBuilder = new StringBuilder();
        this.unrecognizedCodePointBits = new LinkedList<>();
    }

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
        return decompress();
    }

    private void readBytes(InputStream textInputStream) throws IOException {
        ByteArrayOutputStream byteCollection = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = textInputStream.read(buffer)) != -1) {
            byteCollection.write(buffer, 0, length);
        }
        this.bytesToDecode = byteCollection.toByteArray();
    }

    private void readDictionary(InputStream dictionaryInputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInput = new ObjectInputStream(dictionaryInputStream);
        this.dictionary = (Dictionary) objectInput.readObject();
    }

    private String decompress() {
        decodeAllButLast(bytesToDecode);
        decodeLast(bytesToDecode);
        return decodedTextBuilder.toString();
    }

    private void decodeAllButLast(byte[] bytesToDecode) {
        for (int item = 0; item < bytesToDecode.length - 1; item++) {
            decode(bytesToDecode[item], 8);
        }
    }

    private void decodeLast(byte[] bytesToDecode) {
        decode(bytesToDecode[bytesToDecode.length - 1], dictionary.getBitsInLastByte());
    }

    private void decode(byte byteToDecode, int usefulBitsInByte) {
        for (int bitIndex = 0; bitIndex < usefulBitsInByte; bitIndex++) {
            boolean bit = getBit(byteToDecode, bitIndex);
            unrecognizedCodePointBits.add(bit);
            recognize(unrecognizedCodePointBits);
        }
    }

    private void recognize(List<Boolean> unrecognizedCodePointBits) {
        Integer codePoint = dictionary.find(unrecognizedCodePointBits);
        if (codePoint != null) {
            decodedTextBuilder.appendCodePoint(codePoint);
            unrecognizedCodePointBits.clear();
        }
    }

    private boolean getBit(byte b, int bitIndex) {
        byte mask = (byte)(1 << bitIndex);
        return (b & mask) == mask;
    }

}
