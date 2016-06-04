package com.daniilyurov.training.patterns.strategy.game.test.characters;

import com.daniilyurov.training.patterns.strategy.game.test.Character;
import com.daniilyurov.training.patterns.strategy.game.test.properties.flying.MagicFlyingProperty;

public class LostSoul extends Character {

    public LostSoul() {
        flyingProperty = new MagicFlyingProperty();
    }
    @Override
    public void identify() {
        System.out.println("I am a lost soul!");
    }
}
