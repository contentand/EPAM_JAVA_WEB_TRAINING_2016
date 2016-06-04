package com.daniilyurov.training.patterns.strategy.game.test.characters;

import com.daniilyurov.training.patterns.strategy.game.test.Character;
import com.daniilyurov.training.patterns.strategy.game.test.properties.flying.MagicFlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.test.properties.walking.UsualWalkingProperty;

public class BabaYaga extends Character {

    public BabaYaga() {
        flyingProperty = new MagicFlyingProperty();
        walkingProperty = new UsualWalkingProperty();
    }

    @Override
    public void identify() {
        System.out.println("I am a baba yaga!");
    }
}
