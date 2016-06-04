package com.daniilyurov.training.patterns.strategy.game.test;

import com.daniilyurov.training.patterns.strategy.game.Character;
import com.daniilyurov.training.patterns.strategy.game.characters.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import static org.junit.Assert.*;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class GameCharactersIntegrationTest {

    @Rule
    public SystemOutRule outRule = new SystemOutRule().enableLog();

    @Test
    public void test() {

        List<Character> characters = new ArrayList<>();
        characters.add(new BabaYaga());
        characters.add(new Elf());
        characters.add(new Harpy());
        characters.add(new LostSoul());
        characters.add(new Orc());
        characters.add(new Pegasus());
        characters.add(new Troll());
        characters.add(new Vampire());

        for (Character character : characters) {
            character.identify();
            character.walk();
            character.fly();
            System.out.println();
        }

        String expectedOutput = "I am a baba yaga!\n" +
                "Walking...\n" +
                "Flying with magic!\n" +
                "\n" +
                "I am an elf!\n" +
                "Walking...\n" +
                "\n" +
                "I am a harpy\n" +
                "Walking...\n" +
                "Flying!\n" +
                "\n" +
                "I am a lost soul!\n" +
                "Flying with magic!\n" +
                "\n" +
                "I am an Oooorc!\n" +
                "Walking...\n" +
                "\n" +
                "I am a beautiful pegasus!\n" +
                "Walking...\n" +
                "Flying!\n" +
                "\n" +
                "I am a Troll!!\n" +
                "Walking...\n" +
                "\n" +
                "I am a bloody vampire!\n" +
                "Walking...\n" +
                "Flying with magic!\n\n";

        String actualOutput = outRule.getLogWithNormalizedLineSeparator();
        outRule.clearLog();

        assertEquals(expectedOutput, actualOutput);
    }
}
