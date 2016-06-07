package com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.core;

import com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.Bacteria;

/**
 * Flyweight
 */
public abstract class BacteriaCore {
    protected int lifeExpectancy;
    protected int foodHungerLevel;
    protected int reproductionHungerLevel;
    protected String[] favouriteFood;
    protected String[] hazardousEnvironment;
    protected String[] favourableEnvironment;
    protected String[] naturalEnemies;
    protected String[] naturalFriends;
}
