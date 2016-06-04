package com.daniilyurov.training.patterns.strategy.game.characters;

import com.daniilyurov.training.patterns.strategy.game.Character;
import com.daniilyurov.training.patterns.strategy.game.properties.flying.MagicFlyingProperty;

public class LostSoul extends Character {

    public LostSoul() {
        flyingProperty = new MagicFlyingProperty();
    }
    @Override
    public void identify() {
        System.out.println("I am a lost soul!");
    }
}
