package com.daniilyurov.training.patterns.flyweight.bacteria;

import com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.Bacteria;
import com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.TerribleBacteria;

import java.util.ArrayList;

/**
 * More bacteria will be generated with this design approach compared to the regular one.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Bacteria> colony = new ArrayList<>();
        try {
            while (true) {
                colony.add(new TerribleBacteria(1, 2, 3));
                System.out.println("Colony has " + colony.size() + " members.");
            }
        } catch (OutOfMemoryError e) {
            System.err.println("No memory left!");
        }
    }
}
