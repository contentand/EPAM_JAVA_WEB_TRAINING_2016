package com.daniilyurov.training.patterns.strategy.game.properties.flying;

import com.daniilyurov.training.patterns.strategy.game.properties.FlyingProperty;

public class NoFlyingProperty implements FlyingProperty {
    @Override
    public void fly() {
        // nothing...
    }
}
