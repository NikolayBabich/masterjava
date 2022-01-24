package ru.javaops.masterjava.matrix;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class MatrixUtil {

    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        final int[][] matrixBTransposed = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrixBTransposed[j][i] = matrixB[i][j];
            }
        }

        CountDownLatch latch = new CountDownLatch(MainMatrix.THREAD_NUMBER);

        final int maxRowsForThread = matrixSize / MainMatrix.THREAD_NUMBER + 1;
        int globalRowNumber = 0;
        while (globalRowNumber < matrixSize) {
            int startRow = globalRowNumber;
            while (globalRowNumber - startRow < maxRowsForThread) {
                globalRowNumber++;
            }
            int endRow = Math.min(globalRowNumber - 1, matrixSize - 1);
            executor.submit(() -> {
                for (int row = startRow; row <= endRow; row++) {
                    final int[] rowA = matrixA[row];
                    for (int col = 0; col < matrixSize; col++) {
                        final int[] columnB = matrixBTransposed[col];
                        int sum = 0;
                        for (int i = 0; i < matrixSize; i++) {
                            sum += rowA[i] * columnB[i];
                        }
                        matrixC[row][col] = sum;
                    }
                }
                latch.countDown();
            });
        }
        latch.await();

        return matrixC;
    }

    // optimized by https://habr.com/ru/post/114797/
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        int[] thatColumn = new int[matrixSize];
        try {
            //noinspection InfiniteLoopStatement
            for (int j = 0; ; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    thatColumn[k] = matrixB[k][j];
                }

                for (int i = 0; i < matrixSize; i++) {
                    int[] thisRow = matrixA[i];
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += thisRow[k] * thatColumn[k];
                    }
                    matrixC[i][j] = sum;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
