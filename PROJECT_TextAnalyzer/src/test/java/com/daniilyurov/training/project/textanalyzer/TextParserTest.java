package com.daniilyurov.training.project.textanalyzer;

import com.daniilyurov.training.project.textanalyzer.model.Sentence;
import com.daniilyurov.training.project.textanalyzer.model.Text;
import com.daniilyurov.training.project.textanalyzer.model.Word;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TextParserTest {

    @Rule
    public SystemOutRule rule = new SystemOutRule().enableLog().mute();

    public Text text1;
    public String string1;

    public Text text2;
    public String string2;

    public Text text3;
    public String string3;

    @Before
    public void setup() throws IOException {
        string1 = "Are you sure you want to delete the current record? " + // 10 words

                "A special feature of delegates is that a single delegate can encapsulate more than one method " +
                "of a matching\nsignature. " + // 20 words

                "These kind of delegates are called 'Multicast Delegates'. " + // 8 words

                "Internally, multicast delegates are sub-types of System.MulticastDelegate, " +
                "which itself is a subclass of System.Delegate. " + // 14 words

                "The most important point to remember about multicast delegates is that " +
                "\"The return type of a multicast delegate type must be void\". " + // 22 words

                "The reason for this limitation is that a multicast delegate may have multiple " +
                "methods in its invocation list. " + // 18 words

                "Since a single delegate (or method) invocation can return only a single value, " +
                "a multicast delegate type must have the void return type. " + // 23 words

                "Isn't it useful and extremely simple at the same time?"; // 10 words
        InputStream input = new ByteArrayInputStream(string1.getBytes(StandardCharsets.UTF_8));
        text1 = TextParser.getText(input);

        string2 = "Console.WriteLine (\"the integral value of {0} is {1}\", criteria, i);";
        input = new ByteArrayInputStream(string2.getBytes(StandardCharsets.UTF_8));
        text2 = TextParser.getText(input);

        string3 = "These kind of delegates are also called 'Multicast delegates'. " +
                "These kind of delegates are also called 'Multicast delegates'.";
        input = new ByteArrayInputStream(string3.getBytes(StandardCharsets.UTF_8));
        text3 = TextParser.getText(input);
    }

    @Test
    public void testTextDeComposition() {
        assertEquals(string2, text2.toString());
    }

    @Test // tests task 2
    public void testGetSentencesOrderedByWordAmountOrder() {
        String one = "Since a single delegate (or method) invocation can return only a single value, " +
                "a multicast delegate type must have the void return type."; // 23 words
        String two = "The most important point to remember about multicast delegates is that " +
                "\"The return type of a multicast delegate type must be void\"."; // 22 words
        String three = "A special feature of delegates is that a single delegate can encapsulate more " +
                "than one method of a matching signature."; // 20 words
        String four = "The reason for this limitation is that a multicast delegate may have multiple " +
                "methods in its invocation list."; // 18 words
        String five = "Internally, multicast delegates are sub-types of System.MulticastDelegate, " +
                "which itself is a subclass of System.Delegate."; // 14 words
        String six = "Are you sure you want to delete the current record?"; // 10 words
        String seven = "Isn't it useful and extremely simple at the same time?"; // 10 words
        String eight = "These kind of delegates are called 'Multicast Delegates'."; // 8 words

        Sentence[] descendingSentences = text1.getSentences(Text.WordAmountOrder.DESCENDING);
        Sentence[] ascendingSentences = text1.getSentences(Text.WordAmountOrder.ASCENDING);

        assertEquals(one, descendingSentences[0].toString());
        assertEquals(two, descendingSentences[1].toString());
        assertEquals(three, descendingSentences[2].toString());
        assertEquals(four, descendingSentences[3].toString());
        assertEquals(five, descendingSentences[4].toString());
        assertEquals(six, descendingSentences[5].toString());
        assertEquals(seven, descendingSentences[6].toString());
        assertEquals(eight, descendingSentences[7].toString());

        assertEquals(eight, ascendingSentences[0].toString());
        assertEquals(six, ascendingSentences[1].toString());
        assertEquals(seven, ascendingSentences[2].toString());
        assertEquals(five, ascendingSentences[3].toString());
        assertEquals(four, ascendingSentences[4].toString());
        assertEquals(three, ascendingSentences[5].toString());
        assertEquals(two, ascendingSentences[6].toString());
        assertEquals(one, ascendingSentences[7].toString());
    }

    @Test // tests task 3
    public void testGetUniqueWordInFirstSentence_ReturnsNullIfNoUniqueWordFound() {
        Word word = text3.getUniqueWordInFirstSentence();

        assertNull(word);
    }

    @Test // tests task 3
    public void testGetUniqueWordInFirstSentence_ReturnsFirstWordIfOnlyOneSentence() throws IOException {
        Word word = text2.getUniqueWordInFirstSentence();

        assertNotNull(word);
        assertEquals("Console.WriteLine", word.toString());
    }

    @Test // tests task 3
    public void testGetUniqueWordInFirstSentence_ReturnsUniqueWord() throws IOException {
        Word word = text1.getUniqueWordInFirstSentence();

        assertNotNull(word);
        assertEquals("you", word.toString());
    }

    @Test // tests task 4
    public void testGetWordSetFromQuestionsOfParticularWordSize() {
        int wordSize = 4;
        Word[] words = text1.getWordSetFromQuestionsOfParticular(wordSize);

        Set<Word> wordSet = new HashSet<>(Arrays.asList(words));
        assertEquals(4, wordSet.size());
        assertTrue(wordSet.contains(new Word("sure")));
        assertTrue(wordSet.contains(new Word("want")));
        assertTrue(wordSet.contains(new Word("same")));
        assertTrue(wordSet.contains(new Word("time")));
    }

    @Test // tests task 5
    public void testGetReversedText() {
        Text reversedText = text3.getReversedText();
        String reversedString = "Delegates kind of delegates are also called 'Multicast these'. " +
                "Delegates kind of delegates are also called 'Multicast these'.";
        assertEquals(reversedString, reversedText.toString());
    }

    @Test // tests task 6
    public void testPrintTextVocabulary() {
        text3.printTextVocabulary(Word.AlphabeticOrder.ASCENDING);
        assertEquals(
                "\talso\n" +
                        "are\n" +
                        "\tcalled\n" +
                        "\tdelegates\n" +
                        "\tkind\n" +
                        "\tmulticast\n" +
                        "\tof\n" +
                        "\tthese\n",
                rule.getLogWithNormalizedLineSeparator());
        rule.clearLog();

        text3.printTextVocabulary(Word.AlphabeticOrder.DESCENDING);
        assertEquals(
                "\tthese\n" +
                        "\tof\n" +
                        "\tmulticast\n" +
                        "\tkind\n" +
                        "\tdelegates\n" +
                        "\tcalled\n" +
                        "\tare\n" +
                        "also\n",
                rule.getLogWithNormalizedLineSeparator());
        rule.clearLog();
    }
}