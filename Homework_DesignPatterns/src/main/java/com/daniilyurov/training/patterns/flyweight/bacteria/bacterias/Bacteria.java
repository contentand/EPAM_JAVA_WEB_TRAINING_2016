package com.daniilyurov.training.patterns.flyweight.bacteria.bacterias;

import com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.core.BacteriaCore;

/**
 * Client
 */
public abstract class Bacteria {
    protected BacteriaCore core;
    protected int x;
    protected int y;
    protected int z;
}
