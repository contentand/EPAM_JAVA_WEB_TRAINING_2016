package com.daniilyurov.training.patterns.fabricmethod.tetris.test.creators;


import com.daniilyurov.training.patterns.fabricmethod.tetris.test.Figure;
import com.daniilyurov.training.patterns.fabricmethod.tetris.test.FigureCreator;
import com.daniilyurov.training.patterns.fabricmethod.tetris.test.figures.*;

import java.util.Random;

/**
 * Concrete Factory containing static factory implementation.
 */

public class RandomFigureCreator extends FigureCreator {

    private Random random;
    private BasicFigure[] basicFigures;
    private int basicFigureCount;

    public RandomFigureCreator() {
        this.random = new Random(System.currentTimeMillis());
        this.basicFigures = BasicFigure.values();
        this.basicFigureCount = basicFigures.length;
    }

    @Override
    public Figure createFigure() {
        int randomIndex = random.nextInt(basicFigureCount);
        BasicFigure figureType = basicFigures[randomIndex];
        Figure basicFigure  = figureType.getFigure();

        boolean isSuperFigure = random.nextInt(10) == 0; // 10% chance
        if (isSuperFigure) {
            return new SuperFigure(basicFigure);
        }

        return basicFigure;
    }

    private enum BasicFigure {
        I {
            @Override
            Figure getFigure() {
                return new FigureI();
            }
        }, J {
            @Override
            Figure getFigure() {
                return new FigureJ();
            }
        }, L {
            @Override
            Figure getFigure() {
                return new FigureL();
            }
        }, O {
            @Override
            Figure getFigure() {
                return new FigureO();
            }
        }, S {
            @Override
            Figure getFigure() {
                return new FigureS();
            }
        }, T {
            @Override
            Figure getFigure() {
                return new FigureT();
            }
        }, Z {
            @Override
            Figure getFigure() {
                return new FigureZ();
            }
        };

        abstract Figure getFigure();
    }
}
