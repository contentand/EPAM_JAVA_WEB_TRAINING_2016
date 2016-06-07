package com.daniilyurov.training.patterns.flyweight.bacteria.bacterias;

import com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.core.BacteriaCoreFactory;

/**
 * Concrete client
 */
public class TerribleBacteria extends Bacteria {
    public TerribleBacteria(int x, int y, int z) {
        this.core = BacteriaCoreFactory.getBacteriaCore("Terrible");
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
