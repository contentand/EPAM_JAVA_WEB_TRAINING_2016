package com.daniilyurov.training.patterns.fabricmethod.tetris.test;

import java.util.stream.Stream;

/**
 * Client
 */
public class TetrisComponent {

    public void produceFigures(FigureCreator figureCreator, int amount) {
        Stream.generate(figureCreator::createFigure)
                .limit(amount)
                .map(Figure::getDescription)
                .forEach(System.out::println);
    }

    //... other component logic
}
