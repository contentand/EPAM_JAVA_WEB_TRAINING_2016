package com.daniilyurov.training.project.textanalyzer.model;

import java.util.*;

/**
 * The {@code Text} class represents a composition of sentences that belong to a particular text.
 * It has various methods to analyze and retrieve specific information from it.
 * The class is immutable. Once created, it does not change its state.
 *
 * @author Daniil Yurov
 */
public class Text {
    /** Sentences the text consists of.
     *  Since the text is immutable there is no need for dynamic array */
    private Sentence[] sentences;

    /**
     * Allocates a new {@code Text} so that it represents the sequence of
     * Sentences currently contained in the Sentence array argument.
     *
     * @param sentences
     *        The array of initial sentences of the Text.
     *
     * @throws IllegalArgumentException
     *         If the {@code Sentence} array is null.
     *         If the {@code Sentence} array is empty.
     *         If the {@code Sentence} array contains null values.
     */
    public Text(Sentence[] sentences) {
        if (sentences == null) throw new IllegalArgumentException();
        if (sentences.length == 0) throw new IllegalArgumentException();
        for (Sentence sentence: sentences) {
            if (sentence == null) throw new IllegalArgumentException();
        }

        this.sentences = Arrays.copyOf(sentences, sentences.length);
    }

    /**
     * Returns a {@code Sentence} array containing all sentences within the {@code Text}
     * sorted by word amount in the indicated order.
     *
     * @param order
     *        The order in which sentences are to be sorted.
     *
     * @return the sorted {@code} Sentence} array.
     */
    public Sentence[] getSentences(WordAmountOrder order) {
        Sentence[] result = Arrays.copyOf(sentences, sentences.length);
        Arrays.sort(result, order.comparator);
        return result;
    }

    /**
     * Returns a copy of the first {@code Word} in the first sentence that cannot be found in any other sentence
     * or {@code null} if no such word is found. If the {@code Text} consists of one sentence, the first
     * word is returned. The contents of the {@code Word} are copied; subsequent modification of
     * the {@code Word} does not affect the {@code Text} instance.
     *
     * @return a copy of the unique {@code Word}
     */
    public Word getUniqueWordInFirstSentence() {
        for (Word word : getWordsInFirstSentence()) {
            if (isUnique(word)) {
                return word.getCopy();
            }
        }
        return null;
    }

    /**
     * Returns a {@code Word} array of words that are of particular length and
     * are found in all questions in the text.
     * The words are collected in way that they do not repeat.
     *
     * @param wordSize
     *        Number of characters a word should contain.
     * @return a {@code Word} array containing non-repeating words found in questions.
     */
    public Word[] getWordSetFromQuestionsOfParticular(int wordSize) {
        Set<Word> wordSet = new HashSet<>();
        fillWordsFromQuestionsInto(wordSet, wordSize);
        return Word.getWordArrayFrom(wordSet);
    }

    /**
     * Returns a new instance of {@code Text} containing sentences
     * with first word being swapped with the last one.
     *
     * @return a new {@code Text} with swapped words.
            */
    public Text getReversedText() {
        Sentence[] reversedSentences = new Sentence[sentences.length];
        for (int i = 0; i < sentences.length; i++) {
            reversedSentences[i] = sentences[i].swapFirstWordWithLast();
        }
        return new Text(reversedSentences);
    }

    /**
     * Prints vocabulary list sorted in a particular alphabetic order into console.
     * Vocabulary is based on words found in the text.
     * Each time a word starts with a new letter, it is preceded by a tab.
     *
     * @param order
     *        The order in which words should be printed.
     */
    public void printTextVocabulary(Word.AlphabeticOrder order) {
        Set<Word> vocabulary = new TreeSet<>(order.comparator);
        fillWordsFromTextInto(vocabulary);
        printWordsFrom(vocabulary);
    }

    /**
     * Returns the text as a {@code String}.
     *
     * @return a {@code String} containing the text.
     */
    @Override
    public String toString() {
        String[] result = new String[sentences.length];
        for (int i = 0; i < sentences.length; i++) {
            result[i] = sentences[i].toString();
        }
        return String.join(" ", result);
    }

    /**
     * A {@code WordAmountOrder} represents a collection of comparators to be used
     * to compare two {@code Sentence} instances based on the number of word they contain.
     * The comparator is either ascending or descending.
     *
     * @author Daniil Yurov
     */
    public enum WordAmountOrder {
        /**
         * Comparator representing descending order.
         */
        DESCENDING((first, second) -> second.getWordCount() - first.getWordCount()),
        /**
         * Comparator representing ascending order.
         */
        ASCENDING((first, second) -> first.getWordCount() - second.getWordCount());

        Comparator<Sentence> comparator;
        WordAmountOrder(Comparator<Sentence> comparator) {
            this.comparator = comparator;
        }
    }

    private void fillWordsFromTextInto(Set<Word> vocabulary) {
        for (Sentence sentence : sentences) {
            for (Word word : sentence.getWords()) {
                word = word.toLowerCase();
                vocabulary.add(word);
            }
        }
    }

    private void fillWordsFromQuestionsInto(Set<Word> result, int wordSize) {
        for (Sentence sentence : sentences) {
            if (sentence.isQuestion()) {
                Collections.addAll(result, sentence.getWords(wordSize));
            }
        }
    }

    private void printWordsFrom(Set<Word> vocabulary) {
        char previousWordFirstLetter = ' ';
        for (Word word : vocabulary) {
            char currentWordFirstLetter = word.getFirstLetterUpperCase();
            if (currentWordFirstLetter != previousWordFirstLetter) {
                previousWordFirstLetter = currentWordFirstLetter;
                System.out.print("\t");
            }
            System.out.println(word);
        }
    }

    private Word[] getWordsInFirstSentence() {
        int FIRST = 0;
        Sentence firstSentence = sentences[FIRST];
        return firstSentence.getWords();
    }

    private boolean isUnique(Word word) {
        int SECOND = 1;
        for (int index = SECOND; index < sentences.length; index++) {
            if (sentences[index].contains(word)) {
                return false;
            }
        }
        return true;
    }
}
