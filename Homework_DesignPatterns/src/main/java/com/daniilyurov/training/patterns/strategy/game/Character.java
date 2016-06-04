package com.daniilyurov.training.patterns.strategy.game;

import com.daniilyurov.training.patterns.strategy.game.properties.FlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.properties.WalkingProperty;
import com.daniilyurov.training.patterns.strategy.game.properties.flying.NoFlyingProperty;
import com.daniilyurov.training.patterns.strategy.game.properties.walking.NoWalkingProperty;

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
