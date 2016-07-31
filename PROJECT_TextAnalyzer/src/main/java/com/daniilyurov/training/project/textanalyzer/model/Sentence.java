package com.daniilyurov.training.project.textanalyzer.model;

import java.util.*;

/**
 * The {@code Sentence} class represents a composition of words as well as a number
 * of non-word characters that either go before or after the word.
 * Unnecessary whitespaces are not stored and restored upon toString request.
 *
 * It has various methods to analyze and retrieve specific information from it.
 * The class is immutable. Once created, it does not change its state.
 *
 * @author Daniil Yurov
 */
public class Sentence {
    /** Words the sentence consists of. */
    private Word[] words;
    /** Word index and non-word characters that go before it. */
    private Map<Integer, NonWord> preWord;
    /** Word index and non-word characters that go after it. */
    private Map<Integer, NonWord> postWord;

    /**
     * Allocates a new {@code Sentence} so that it represents the sequence of
     * Words currently contained in the Sentence array argument as well as
     * a number of characters that go before or after each word.
     *
     * @param words
     *        The array of initial Words of the Sentence.
     * @param preWord
     *        The map containing word index and NonWord that should go before the Word.
     * @param postWord
     *        The map containing word index and NonWord that should go after the Word.
     *
     * @throws IllegalArgumentException
     *         If any parameter is null.
     *         If any parameter contains null values.
     */
    public Sentence(Word[] words, Map<Integer, NonWord> preWord, Map<Integer, NonWord> postWord){
        if (words == null || preWord == null || postWord == null) {
            throw new IllegalArgumentException();
        }
        for (Word word : words) {
            if (word == null) {
                throw new IllegalArgumentException();
            }
        }
        if (preWord.containsKey(null) || preWord.containsValue(null)
                || postWord.containsKey(null) || postWord.containsValue(null)) {
            throw new IllegalArgumentException();
        }

        this.words = Arrays.copyOf(words, words.length);
        this.preWord = new HashMap<>(preWord);
        this.postWord = new HashMap<>(postWord);
    }

    /**
     * Returns number of words in the sentence.
     */
    public int getWordCount() {
        return words.length;
    }

    /**
     * Checks if the passed {@code Word} is in the sentence.
     *
     * @param word
     *        The {@code Word} to be checked.
     * @return
     *        {@code true} if the {@code Word} with the same letters is in the sentence.
     */
    public boolean contains(Word word) {
        for (Word w : words) {
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code Word} array of all words within the sentence.
     */
    public Word[] getWords() {
        return Arrays.copyOf(words, words.length);
    }

    /**
     * Checks if the current sentence is a question.
     *
     * @return {@code true} if question.
     */
    public boolean isQuestion() {
        int sentencePunctuationIndex = words.length - 1;
        NonWord sentencePunctuation = postWord.get(sentencePunctuationIndex);
        return sentencePunctuation.isQuestion();
    }

    /**
     * Returns {@code Word} array with words of particular size that
     * are found in the sentence.
     *
     * @param wordSize
     *        The size of words to look for.
     * @return {@code Word} array of matched words.
     */
    public Word[] getWords(int wordSize) {
        Set<Word> result = new HashSet<>();
        for (Word word: words) {
            if (word.size() == wordSize) {
                result.add(word);
            }
        }
        Word[] w = new Word[result.size()];
        result.toArray(w);
        return w;
    }

    /**
     * Returns a new instance of {@code Sentence} with first and last words swapped.
     */
    public Sentence swapFirstWordWithLast() {
        if (words.length < 1) {
            return new Sentence(Arrays.copyOf(words, words.length), new HashMap<>(preWord), new HashMap<>(postWord));
        }

        Word[] reversedWords = Arrays.copyOf(words, words.length);
        Word first = reversedWords[0];
        Word last = reversedWords[words.length - 1];
        boolean isFirstCapital = first.isFirstLetterUppercase();
        boolean isLastCapital = last.isFirstLetterUppercase();

        reversedWords[0] = isFirstCapital ?
                last.setFirstLetterToUpperCase() : last.setFirstLetterToLowerCase();
        reversedWords[words.length - 1] = isLastCapital ?
                first.setFirstLetterToUpperCase() : first.setFirstLetterToLowerCase();
        return new Sentence(reversedWords, new HashMap<>(preWord), new HashMap<>(postWord));
    }

    /**
     * Returns the Sentence as a {@code String}.
     *
     * @return a {@code String} containing the sentence text.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (postWord.containsKey(-1)) {
            result.append(postWord.get(-1));
            result.append(" ");
        }

        for (int i = 0; i < words.length; i++) {
            if (preWord.containsKey(i)) {
                result.append(preWord.get(i));
            }
            result.append(words[i]);
            if (postWord.containsKey(i)) {
                result.append(postWord.get(i));
            }
            result.append(" ");
        }

        return result.toString().trim();
    }

}
