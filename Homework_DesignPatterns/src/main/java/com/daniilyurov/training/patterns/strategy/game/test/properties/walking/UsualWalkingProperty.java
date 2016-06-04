package com.daniilyurov.training.patterns.strategy.game.test.properties.walking;

import com.daniilyurov.training.patterns.strategy.game.test.properties.WalkingProperty;

public class UsualWalkingProperty implements WalkingProperty {
    @Override
    public void walk() {
        System.out.println("Walking...");
    }
}
