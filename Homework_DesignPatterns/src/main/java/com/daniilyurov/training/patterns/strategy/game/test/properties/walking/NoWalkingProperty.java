package com.daniilyurov.training.patterns.strategy.game.test.properties.walking;

import com.daniilyurov.training.patterns.strategy.game.test.properties.WalkingProperty;

public class NoWalkingProperty implements WalkingProperty {
    @Override
    public void walk() {
        // nothing...
    }
}
