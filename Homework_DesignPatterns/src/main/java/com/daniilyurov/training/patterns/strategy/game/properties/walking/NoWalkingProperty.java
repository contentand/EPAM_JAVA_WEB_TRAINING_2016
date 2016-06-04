package com.daniilyurov.training.patterns.strategy.game.properties.walking;

import com.daniilyurov.training.patterns.strategy.game.properties.WalkingProperty;

public class NoWalkingProperty implements WalkingProperty {
    @Override
    public void walk() {
        // nothing...
    }
}
