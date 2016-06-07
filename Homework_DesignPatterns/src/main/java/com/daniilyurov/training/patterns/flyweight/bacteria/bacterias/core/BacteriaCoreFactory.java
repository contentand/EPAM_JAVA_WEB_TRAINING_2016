package com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight Factory
 */
public class BacteriaCoreFactory {

    private static Map<String, BacteriaCore> cores = new HashMap<>();

    private BacteriaCoreFactory(){}

    public static BacteriaCore getBacteriaCore(String type) {
        switch (type) {
            case "Terrible":
                BacteriaCore core = cores.get(type);
                if (core == null) {
                    core = new TerribleBacteriaCore();
                    cores.put(type, core);
                    return core;
                }
            default:
                throw new IllegalArgumentException("No such bacteria type : " + type);
        }
    }
}
