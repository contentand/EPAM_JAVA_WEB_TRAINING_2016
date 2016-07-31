package com.daniilyurov.training.project.textanalyzer;

import com.daniilyurov.training.project.textanalyzer.model.Text;
import com.daniilyurov.training.project.textanalyzer.model.Word;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        Long start = System.currentTimeMillis();
        InputStream io = new FileInputStream("C://ebook.TXT");
        Text text = TextParser.getText(io);
        System.out.println("TIME TO DECOMPOSE TEXT : " + (System.currentTimeMillis() - start));
        text.printTextVocabulary(Word.AlphabeticOrder.ASCENDING);
        System.out.println(text);
//        for (Sentence sentence : text.getSentences(Text.WordAmountOrder.ASCENDING)) {
//            System.out.println(sentence);
//        }
    }

}
