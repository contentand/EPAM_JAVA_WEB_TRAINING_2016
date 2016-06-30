package com.daniilyurov.training.concurrency.violinists.test;

import com.daniilyurov.training.concurrency.violinists.Performance;
import com.daniilyurov.training.concurrency.violinists.Violinist;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ViolinistsTest {

    public Random random = new Random(System.currentTimeMillis());

    @Test
    public void test() {
        int SECONDS = 10;
        int VIOLINISTS;
        int BOWS;
        int VIOLINS;

//        VIOLINISTS = random.nextInt(20) + 5; // minimum 5, maximum 25.
//        BOWS = IntStream.range(VIOLINISTS / 4, VIOLINISTS - 2).parallel().findAny().getAsInt();
//        VIOLINS = VIOLINISTS - BOWS - random.nextInt(VIOLINISTS - BOWS - 1);

        VIOLINISTS = 14;
        BOWS = 7;
        VIOLINS = 2;

        System.out.println("VIOLINISTS : " + VIOLINISTS + ", BOWS : " + BOWS + ", VIOLINS : " + VIOLINS);
        Assert.assertTrue(VIOLINISTS > (VIOLINS + BOWS));
        List<Violinist> violinists = Performance.perform(VIOLINISTS,VIOLINS, BOWS, SECONDS);
        violinists.forEach(violinist ->
                System.out.println(violinist.getName() + " : " + violinist.getPerformedTimes()));
    }
}
