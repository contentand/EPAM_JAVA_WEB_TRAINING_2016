package com.daniilyurov.training.patterns.strategy.game.test.characters;

import com.daniilyurov.training.patterns.strategy.game.test.Character;
import com.daniilyurov.training.patterns.strategy.game.test.properties.flying.MagicFlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.test.properties.walking.UsualWalkingProperty;

public class Vampire extends Character {

    public Vampire() {
        walkingProperty = new UsualWalkingProperty();
        flyingProperty = new MagicFlyingProperty();
    }

    @Override
    public void identify() {
        System.out.println("I am a bloody vampire!");
    }
}
