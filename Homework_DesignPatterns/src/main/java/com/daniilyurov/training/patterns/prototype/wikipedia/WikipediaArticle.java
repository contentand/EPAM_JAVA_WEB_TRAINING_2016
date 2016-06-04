package com.daniilyurov.training.patterns.prototype.wikipedia;

/**
 * Contains information about the article
 */
public class WikipediaArticle implements Cloneable {
    private final int id;
    private String author;
    private String title;
    private String text;

    public WikipediaArticle(int id, String author, String title, String text) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected WikipediaArticle clone() throws CloneNotSupportedException {
        return (WikipediaArticle) super.clone();
    }
}
