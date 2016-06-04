package com.daniilyurov.training.patterns.strategy.game.properties.flying;

import com.daniilyurov.training.patterns.strategy.game.properties.FlyingProperty;

public class MagicFlyingProperty implements FlyingProperty {
    @Override
    public void fly() {
        System.out.println("Flying with magic!");
    }
}
