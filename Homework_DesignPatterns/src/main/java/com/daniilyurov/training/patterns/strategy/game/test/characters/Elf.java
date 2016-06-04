package com.daniilyurov.training.patterns.strategy.game.test.characters;

import com.daniilyurov.training.patterns.strategy.game.test.Character;
import com.daniilyurov.training.patterns.strategy.game.test.properties.walking.UsualWalkingProperty;

public class Elf extends Character {

    public Elf() {
        walkingProperty = new UsualWalkingProperty();
    }

    @Override
    public void identify() {
        System.out.println("I am an elf!");
    }
}
