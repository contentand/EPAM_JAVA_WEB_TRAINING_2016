package com.daniilyurov.training.concurrency.matrix;

public final class MathMultiplier {

    // to insure no instantiation.
    private MathMultiplier(){}

    public static int[][] multiplyWithASingleThread(int[][] matrix1, int[][] matrix2) {
        int width = matrix1.length;
        int[][] result = new int[width][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                int res = 0;

                for (int g = 0; g < width; g++) {
                    res += matrix1[x][g] * matrix2[g][y];
                }

                result[x][y] = res;
            }
        }

        return result;
    }

    public static int[][] multiply(int[][] matrix1, int[][] matrix2, int threads) throws InterruptedException {

        /**
         * MATRIX :
         *   |   1         2
         * --------------------
         * A | cell_A1, cell_A2
         * B | cell_B1, cell_B2
         *
         * CELL - the value. Matrix above has 4 cells.
         * WIDTH - number of columns in one row or number of rows in one column. Matrix above has width of 2.
         *
         * !The smallest unit a thread can calculate in current implementation is one cell.
         * One result cell cannot be calculated by multiple threads.
         * The matrix above can be processed by 4 threads at most.
         */

        // Create an empty result matrix
        final int width = matrix1.length;
        final int[][] result = new int[width][width];

        // Ensure there are no more threads than cells.
        final int cells = width * width;
        if (threads > cells) threads = cells;

        // How many cells should each thread calculate.
        final int cellsPerThread = cells / threads;

        // Ensure the last thread calculates cells that could not be evenly spread across all threads.
        final int cellUnallocatedEvenly = cells % threads;
        int allocatedThreads = cellUnallocatedEvenly == 0 ? threads : --threads;

        // Calculate allocated cells
        for (int threadCount = 0; threadCount < allocatedThreads; threadCount++) {
            int startCellIndex = cellsPerThread * threadCount;
            int finalCellIndex = startCellIndex + cellsPerThread;
            multiply(matrix1, matrix2, result, startCellIndex, finalCellIndex);
        }

        // Let the last thread calculate unallocated cells.
        if (cellUnallocatedEvenly > 0) {
            int startCellIndex = cellsPerThread * allocatedThreads;
            int finalCellIndex = startCellIndex + cellUnallocatedEvenly;
            multiply(matrix1, matrix2, result, startCellIndex, finalCellIndex);
        }

        return result;
    }

    private static void multiply(final int[][] m1, final int[][]m2, final int[][] result, final int startCellIndex,
                                 final int finalCellIndex) throws InterruptedException {

        Thread th = new Thread(() -> {
            int width = m1.length;

            for (int i = startCellIndex; i < finalCellIndex; i++) {
                int x = i / width;
                int y = i % width;
                result[x][y] = getMultipliedCellValue(m1, m2, x, y);
            }
        });

        th.start();
        th.join();

    }

    private static int getMultipliedCellValue(final int[][] m1, final int[][]m2, final int x, final int y) {

        int result = 0;
        for (int i = 0; i < m1.length; i++) {
            result += m1[x][i] * m2[i][y];
        }
        return result;

    }

}
