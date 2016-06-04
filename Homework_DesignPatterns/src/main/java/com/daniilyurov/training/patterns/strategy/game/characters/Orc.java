package com.daniilyurov.training.patterns.strategy.game.characters;

import com.daniilyurov.training.patterns.strategy.game.Character;
import com.daniilyurov.training.patterns.strategy.game.properties.walking.UsualWalkingProperty;

public class Orc extends Character {

    public Orc() {
        walkingProperty = new UsualWalkingProperty();
    }

    @Override
    public void identify() {
        System.out.println("I am an Oooorc!");
    }
}
