package com.daniilyurov.training.project.textanalyzer.model;

import java.util.Comparator;
import java.util.Set;

/**
 * The {@code Word} class contains a string of word.
 * It has various methods to analyze and retrieve specific information from it.
 * The class is immutable. Once created, it does not change its state.
 *
 * @author Daniil Yurov
 */
public class Word {
    /** The word. */
    private String word;

    /**
     * Allocates a new {@code Word} instance.
     *
     * @param word
     *        The word the {@code Word} instance should contain.
     * @throws IllegalArgumentException
     *         If null or empty {@code String} is passed as an argument.
     */
    public Word(String word) {
        if (word == null || word.length() < 1) {
            throw new IllegalArgumentException();
        }

        this.word = word;
    }

    /**
     * Returns the number of chars in the word.
     */
    public int size() {
        return word.length();
    }

    /**
     * Returns the first {@code char} in uppercase.
     */
    public char getFirstLetterUpperCase() {
        return Character.toUpperCase(word.charAt(0));
    }

    /**
     * Returns a new instance of {@code Word} with all its
     * letters in lowercase.
     */
    public Word toLowerCase() {
        return new Word(word.toLowerCase());
    }

    /**
     * Checks if the first letter is uppercase.
     * @return {@code true} if the first letter is uppercase.
     */
    public boolean isFirstLetterUppercase() {
        return Character.isUpperCase(word.charAt(0));
    }

    /**
     * Returns a new instance of {@code Word} with the first letter in lowercase.
     */
    public Word setFirstLetterToLowerCase() {
        return new Word(word.substring(0,1).toLowerCase() + word.substring(1));
    }

    /**
     * Returns a new instance of {@code Word} with the first letter in uppercase.
     */
    public Word setFirstLetterToUpperCase() {
        return new Word(word.substring(0,1).toUpperCase() + word.substring(1));
    }

    /**
     * Returns a new identical instance of {@code Word}.
     */
    public Word getCopy() {
        return new Word(word);
    }

    /**
     * Transforms a {@code Word} set into a {@code Word} array.
     * @param wordSet
     *        A set containing instances of {@code Word}.
     * @return {@code Word} array composed of set elements.
     */
    public static Word[] getWordArrayFrom(Set<Word> wordSet) {
        Word[] words = new Word[wordSet.size()];
        wordSet.toArray(words);
        return words;
    }

    /**
     * A {@code AlphabeticOrder} represents a collection of comparators to be used
     * to compare two {@code Word} instances based on lexicographic comparison.
     * The comparator is either ascending or descending.
     *
     * @author Daniil Yurov
     */
    public enum AlphabeticOrder {
        /**
         * Comparator representing ascending order.
         */
        ASCENDING((word1, word2) -> word1.word.compareToIgnoreCase(word2.word)),
        /**
         * Comparator representing descending order.
         */
        DESCENDING((word1, word2) -> word2.word.compareToIgnoreCase(word1.word));

        Comparator<Word> comparator;
        AlphabeticOrder(Comparator<Word> comparator) {
            this.comparator = comparator;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word otherWord = (Word) o;

        return otherWord.word.toUpperCase().equals(word.toUpperCase());
    }

    @Override
    public int hashCode() {
        return word.toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return word;
    }
}
