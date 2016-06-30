package com.daniilyurov.training.concurrency.matrix.test;

import com.daniilyurov.training.concurrency.matrix.MathMultiplier;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class MatrixTest {

    public Random random = new Random(System.currentTimeMillis());

    @Test
    public void test() throws InterruptedException {

        Instant start;
        Instant end;

        int width = 1000;
        int threads = Runtime.getRuntime().availableProcessors();
        int[][] matrix1 = generateRandomMatrix(width);
        int[][] matrix2 = generateRandomMatrix(width);

        start = Instant.now();
        int[][] singleThreadedCalculation = MathMultiplier.multiplyWithASingleThread(matrix1, matrix2);
        end = Instant.now();
        long singleThreadedCalculationTime = Duration.between(start, end).toMillis();

        start = Instant.now();
        int[][] multiThreadedCalculation = MathMultiplier.multiply(matrix1, matrix2, threads);
        end = Instant.now();
        long multiThreadedCalculationTime = Duration.between(start, end).toMillis();

        assertEquals(Arrays.deepToString(singleThreadedCalculation), Arrays.deepToString(multiThreadedCalculation));
        assertTrue(singleThreadedCalculationTime > multiThreadedCalculationTime);
    }



    private int[][] generateRandomMatrix(int width) {
        int[][] result = new int[width][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                result[x][y] = random.nextInt(50);
            }
        }

        return result;
    }
}
