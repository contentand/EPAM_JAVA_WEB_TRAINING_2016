package com.daniilyurov.training.patterns.prototype.wikipedia.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WikipediaArticleFileIntegrationTest {

    public WikipediaArticleFile file;

    @Before
    public void setup() {
        file = new WikipediaArticleFile();
    }

    @Test
    public void shouldReturnPrototype() {
        assertTrue(file.getArticle(1).isPresent());
    }

    @Test
    public void modifyPrototype_hasNoEffectOnOriginal() {
        WikipediaArticle prototype = file.getArticle(1).get();
        prototype.setAuthor("OTHER");
        prototype.setTitle("OTHER");
        prototype.setText("OTHER");

        WikipediaArticle original = file.getOriginalArticle(prototype).get();
        assertNotEquals(prototype.getAuthor(), original.getAuthor());
        assertNotEquals(prototype.getTitle(), original.getTitle());
        assertNotEquals(prototype.getText(), original.getText());
    }
}
