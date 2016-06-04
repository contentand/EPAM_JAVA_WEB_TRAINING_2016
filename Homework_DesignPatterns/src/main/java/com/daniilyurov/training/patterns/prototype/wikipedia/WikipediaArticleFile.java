package com.daniilyurov.training.patterns.prototype.wikipedia;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contains wikipedia articles in immutable state.
 */
public final class WikipediaArticleFile {
    private Map<Integer, WikipediaArticle> articles;

    public WikipediaArticleFile(){
        articles = new HashMap<>();
        WikipediaArticle article;
        int index = 0;

        article = new WikipediaArticle(++index, "Sam", "On Cars", "Cars are fast and furious...");
        articles.put(index, article);

        article = new WikipediaArticle(++index, "Rob", "On Computers", "Computers are genius...");
        articles.put(index, article);

        article = new WikipediaArticle(++index, "Tom", "On Planes", "Planes are safe and fly high...");
        articles.put(index, article);
    }

    public Optional<WikipediaArticle> getArticle(int index) {
        WikipediaArticle result = null;
        try {
            result = articles.get(index).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(result);
    }

    public Optional<WikipediaArticle> getOriginalArticle(WikipediaArticle modifiedArticle) {
        return getArticle(modifiedArticle.getId());
    }
}
