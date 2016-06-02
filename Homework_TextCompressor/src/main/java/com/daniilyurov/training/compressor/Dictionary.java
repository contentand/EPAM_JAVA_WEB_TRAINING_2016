package com.daniilyurov.training.compressor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The container class to hold "code point" - "bit set" pairs.
 * This class is used as a guide to compress and expand text data.
 * It also holds information about the bits which did not fit into a byte.
 * It implements Serializable to be stored in the compressed file.
 */
public class Dictionary implements Serializable {
    private int bitsInLastByte; //how many bits did not fit into the final byte
    private Map<Integer, List<Boolean>> binaryDictionary;
    private Map<List<Boolean>, Integer> reversedDictionary;

    Dictionary(Map<Integer, List<Boolean>> map) {
        this.bitsInLastByte = 0;
        this.reversedDictionary = new HashMap<>();
        map.forEach((key, value) -> reversedDictionary.put(value, key));
        this.binaryDictionary = map;
    }

    List<Boolean> getBits(int codePoint) {
        return binaryDictionary.get(codePoint);
    }

    void setBitsInLastByte(int bitsInLastByte) {
        this.bitsInLastByte = bitsInLastByte;
    }

    int getBitsInLastByte() {
        return this.bitsInLastByte;
    }

    public Integer find(List<Boolean> value) {
        return reversedDictionary.get(value);
    }
}
