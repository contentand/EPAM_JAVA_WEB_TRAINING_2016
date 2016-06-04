package com.daniilyurov.training.patterns.fabricmethod.tetris.figures;

import com.daniilyurov.training.patterns.fabricmethod.tetris.Figure;
/**
 * Concrete Product :: Decorator pattern is chosen to reduce class complexity.
 */
public class SuperFigure extends Figure {
    private Figure baseFigure;

    public SuperFigure(Figure baseFigure) {
        this.baseFigure = baseFigure;
    }

    @Override
    public String getDescription() {
        return "Super " + baseFigure.getDescription();
    }
}
