package com.daniilyurov.training.patterns.strategy.game.test.properties.flying;

import com.daniilyurov.training.patterns.strategy.game.test.properties.FlyingProperty;

public class UsualFlyingProperty implements FlyingProperty {
    @Override
    public void fly() {
        System.out.println("Flying!");
    }
}
