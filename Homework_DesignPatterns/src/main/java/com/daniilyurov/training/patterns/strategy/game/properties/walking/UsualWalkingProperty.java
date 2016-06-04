package com.daniilyurov.training.patterns.strategy.game.properties.walking;

import com.daniilyurov.training.patterns.strategy.game.properties.WalkingProperty;

public class UsualWalkingProperty implements WalkingProperty {
    @Override
    public void walk() {
        System.out.println("Walking...");
    }
}
