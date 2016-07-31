package com.daniilyurov.training.project.textanalyzer.model;

/**
 * The {@code NonWord} class contains a string of characters that do not belong to a word.
 * It has various methods to analyze and retrieve specific information from it.
 * The class is immutable. Once created, it does not change its state.
 *
 * @author Daniil Yurov
 */
public class NonWord {
    /** A string containing characters that do not belong to a word. */
    private String nonWord;

    /**
     * Allocates a new {@code NonWord} instance.
     *
     * @param nonWord
     *        The word the {@code Word} instance should contain.
     * @throws IllegalArgumentException
     *         If null or empty {@code String} is passed as an argument.
     */
    public NonWord(char ... nonWord) {
        this.nonWord = String.valueOf(nonWord);
    }

    /**
     * Checks if the non word contains question mark.
     * @return {@code true} if question mark is present.
     */
    public boolean isQuestion() {
        return nonWord.contains("?");
    }

    @Override
    public String toString() {
        return nonWord;
    }
}
