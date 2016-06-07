package com.daniilyurov.training.patterns.flyweight.bacteria.bacterias.core;

/**
 * Concrete Flyweight
 */
public class TerribleBacteriaCore extends BacteriaCore {
    public TerribleBacteriaCore() {
        this.lifeExpectancy = 4;
        this.foodHungerLevel = 2;
        this.reproductionHungerLevel = 1;
        this.favouriteFood = new String[]{"Solar Energy"};
        this.hazardousEnvironment = new String[]{"Desert"};
        this.favourableEnvironment = new String[]{"Ponds"};
        this.naturalEnemies = new String[]{"Good"};
        this.naturalFriends = new String[]{"Terrible"};
    }
}
