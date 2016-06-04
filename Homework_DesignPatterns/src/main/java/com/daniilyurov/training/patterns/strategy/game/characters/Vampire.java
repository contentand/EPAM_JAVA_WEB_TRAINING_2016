package com.daniilyurov.training.patterns.strategy.game.characters;

import com.daniilyurov.training.patterns.strategy.game.Character;
import com.daniilyurov.training.patterns.strategy.game.properties.flying.MagicFlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.properties.walking.UsualWalkingProperty;

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
