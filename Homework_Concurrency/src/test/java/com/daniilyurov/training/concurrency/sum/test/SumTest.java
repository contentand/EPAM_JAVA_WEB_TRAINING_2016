package com.daniilyurov.training.concurrency.sum.test;

import com.daniilyurov.training.concurrency.sum.ConcurrentSum;
import org.junit.Assert;
import org.junit.Test;

public class SumTest {

    @Test
    public void test() {
        int result = ConcurrentSum.sum(Runtime.getRuntime().availableProcessors(), 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Assert.assertEquals(45, result);
    }
}
