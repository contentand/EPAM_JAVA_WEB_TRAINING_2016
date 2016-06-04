package com.daniilyurov.training.patterns.strategy.game.test.characters;

import com.daniilyurov.training.patterns.strategy.game.test.Character;
import com.daniilyurov.training.patterns.strategy.game.test.properties.walking.UsualWalkingProperty;

public class Troll extends Character {

    public Troll() {
        walkingProperty = new UsualWalkingProperty();
    }

    @Override
    public void identify() {
        System.out.println("I am a Troll!!");
    }
}
