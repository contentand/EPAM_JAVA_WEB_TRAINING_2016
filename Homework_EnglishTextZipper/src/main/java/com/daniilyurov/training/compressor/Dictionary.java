package com.daniilyurov.training.compressor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary implements Serializable {
    private int bitsInLastByte;
    private Map<Integer, List<Boolean>> binaryDictionary;
    private Map<List<Boolean>, Integer> reversedDictionary;

    Dictionary(Map<Integer, List<Boolean>> map) {
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
