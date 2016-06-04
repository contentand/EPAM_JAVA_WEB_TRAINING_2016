package com.daniilyurov.training.patterns.strategy.game.characters;

import com.daniilyurov.training.patterns.strategy.game.Character;
import com.daniilyurov.training.patterns.strategy.game.properties.flying.MagicFlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.properties.walking.UsualWalkingProperty;

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
