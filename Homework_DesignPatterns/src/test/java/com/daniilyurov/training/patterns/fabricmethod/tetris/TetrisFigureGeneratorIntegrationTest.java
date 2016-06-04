package com.daniilyurov.training.patterns.fabricmethod.tetris;

import com.daniilyurov.training.patterns.fabricmethod.tetris.creators.RandomFigureCreator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class TetrisFigureGeneratorIntegrationTest {

    @Rule
    public SystemOutRule out = new SystemOutRule().enableLog();

    @Test
    public void shouldProduceTheExactAmountAsAskedAndTheResultShouldNotBeTheSame() {
        int amount = 10;

        TetrisComponent component = new TetrisComponent();
        FigureCreator creator = new RandomFigureCreator();

        component.produceFigures(creator, amount);

        String output = out.getLogWithNormalizedLineSeparator();
        out.clearLog();

        // test the amount of new lines
        int newLineCodePoint = "\n".codePointAt(0);
        long newLinesCount = output.codePoints()
                .filter(currentCodePoint -> currentCodePoint == newLineCodePoint)
                .count();
        assertEquals(amount, newLinesCount);

        // test output is random
        List<String> outputValues = Arrays.asList(output.split("\n"));
        Set<String> uniqueOutputValues = new HashSet<>(outputValues);
        assertTrue(uniqueOutputValues.size() > 1);
    }
}
