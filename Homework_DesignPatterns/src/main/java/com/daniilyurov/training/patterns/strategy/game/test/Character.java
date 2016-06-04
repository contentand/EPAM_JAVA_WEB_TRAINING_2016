package com.daniilyurov.training.patterns.strategy.game.test;

import com.daniilyurov.training.patterns.strategy.game.test.properties.FlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.test.properties.WalkingProperty;
import com.daniilyurov.training.patterns.strategy.game.test.properties.flying.NoFlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.test.properties.walking.NoWalkingProperty;

public abstract class Character {
    protected FlyingProperty flyingProperty;
    protected WalkingProperty walkingProperty;

    public Character() {
        this.flyingProperty = new NoFlyingProperty();
        this.walkingProperty = new NoWalkingProperty();
    }

    public void fly() {
        flyingProperty.fly();
    }

    public void walk() {
        walkingProperty.walk();
    }

    public abstract void identify();
}
