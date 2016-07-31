package com.daniilyurov.training.project.textanalyzer;

import com.daniilyurov.training.project.textanalyzer.model.NonWord;
import com.daniilyurov.training.project.textanalyzer.model.Sentence;
import com.daniilyurov.training.project.textanalyzer.model.Text;
import com.daniilyurov.training.project.textanalyzer.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextParser {
    public static Text getText(InputStream input) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String buffer;
        StringBuilder result = new StringBuilder();
        while ((buffer = reader.readLine()) != null) {
            result.append(buffer).append(" ");
        }
        String text = result.toString();
        text = text.replaceAll("[ \t\r\n\f]+", " ");  // remove tabs, new lines and multiple whitespaces

        BreakIterator bi = BreakIterator.getSentenceInstance();
        bi.setText(text);


        List<Sentence> sentences = new ArrayList<>();
        int start = bi.first();
        for (int end = bi.next();
             end != BreakIterator.DONE;
             start = end, end = bi.next()) {

            String sentenceText = text.substring(start, end);
            SentenceParser sentenceParser = new SentenceParser(sentenceText);
            Sentence sentence = sentenceParser.getSentence();
            sentences.add(sentence);

        }
        Sentence[] s = new Sentence[sentences.size()];
        sentences.toArray(s);
        return new Text(s);
    }

    private static class SentenceParser {
        final List<Word> words = new ArrayList<>();
        final Map<Integer, NonWord> preWord = new HashMap<>();
        final Map<Integer, NonWord> postWord = new HashMap<>();

        NonWordBuilder unallocatedNonWord = new NonWordBuilder();
        String currentWord;
        boolean isPreviousWhitespace = true;
        boolean isWhiteSpaceAtTheBeginningRequired = false;

        SentenceParser(String sentence) {
            BreakIterator iterator = BreakIterator.getWordInstance();
            iterator.setText(sentence);
            int start = iterator.first();
            for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
                parseWord(sentence.substring(start, end));
            }
            if (unallocatedNonWord.isNotEmpty()) {
                postWord.put(words.size() - 1, unallocatedNonWord.build());
            }
        }

        Sentence getSentence() {
            Word[] w = new Word[words.size()];
            words.toArray(w);
            return new Sentence(w, preWord, postWord);
        }

        private void parseWord(String word) {
            this.currentWord = word;

            if (isWhiteSpace()) {
                isPreviousWhitespace = true;

                if (unallocatedNonWord.isNotEmpty()) {
                    unallocatedNonWord.add(word);
                } else {
                    isWhiteSpaceAtTheBeginningRequired = true;
                }
                return;
            }

            if (isWord()) {
                words.add(getWord(word));

                if (unallocatedNonWord.isNotEmpty()) {
                    if (isPreviousWhitespace) {
                        unallocatedNonWord.trim(isWhiteSpaceAtTheBeginningRequired);
                        postWord.put(words.size() - 2, unallocatedNonWord.build());
                    } else {
                        preWord.put(words.size() - 1, unallocatedNonWord.build());
                    }
                }

                isWhiteSpaceAtTheBeginningRequired = false;
                unallocatedNonWord.reset();
                return;
            }

            isPreviousWhitespace = false;
            unallocatedNonWord.add(word);
        }

        private Word getWord(String word) {
            return new Word(word);
        }

        private boolean isWord() {
            return currentWord.matches(".*[a-zA-Zа-яА-Я].*");
        }

        private boolean isWhiteSpace() {
            return currentWord.equals(" ");
        }
    }

    private static class NonWordBuilder {
        private String nonWord = "";

        public NonWord build() {
            return new NonWord(nonWord.toCharArray());
        }

        public void reset() {
            this.nonWord = "";
        }

        public void add(String value) {
            this.nonWord += value;
        }

        public boolean isNotEmpty() {
            return !nonWord.isEmpty();
        }

        public void trim(boolean isWhiteSpaceAtTheBeginningRequired) {
            nonWord = nonWord.trim();
            if (isWhiteSpaceAtTheBeginningRequired) {
                nonWord = " " + nonWord;
            }
        }
    }
}
