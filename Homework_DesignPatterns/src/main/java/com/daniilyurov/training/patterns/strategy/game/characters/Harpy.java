package com.daniilyurov.training.patterns.strategy.game.characters;

import com.daniilyurov.training.patterns.strategy.game.Character;
import com.daniilyurov.training.patterns.strategy.game.properties.flying.UsualFlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.properties.walking.UsualWalkingProperty;

public class Harpy extends Character {

    public Harpy() {
        walkingProperty = new UsualWalkingProperty();
        flyingProperty = new UsualFlyingProperty();
    }

    @Override
    public void identify() {
        System.out.println("I am a harpy");
    }
}
