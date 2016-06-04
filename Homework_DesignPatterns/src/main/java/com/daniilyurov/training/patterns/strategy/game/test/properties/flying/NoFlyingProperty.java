package com.daniilyurov.training.patterns.strategy.game.test.properties.flying;

import com.daniilyurov.training.patterns.strategy.game.test.properties.FlyingProperty;

public class NoFlyingProperty implements FlyingProperty {
    @Override
    public void fly() {
        // nothing...
    }
}
