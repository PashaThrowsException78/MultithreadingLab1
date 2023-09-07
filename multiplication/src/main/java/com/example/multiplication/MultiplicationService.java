package com.example.multiplication;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class MultiplicationService {

    private final InputClient inputClient;

    private static final int THREAD_COUNT = 2;

    public MultiplicationService(InputClient inputClient) {
        this.inputClient = inputClient;
        System.out.println("Thread count: " + THREAD_COUNT);
    }

    public List<List<Integer>> multiplicate(int size) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        List<List<Integer>> tasksIJ = new ArrayList<>(THREAD_COUNT);

        int lastBusyIJ = 0;
        for (int counter = 0; counter < THREAD_COUNT; counter++) {
            tasksIJ.add(new ArrayList<>());

            for (int c = lastBusyIJ; c < size; c++) {
                lastBusyIJ = c;
                tasksIJ.get(counter).add(c);
            }
        }

        MatricesDTO matrices = inputClient.getMatrices(size);

        int[][] result = new int[size][size];

        for (int threadNum = 0; threadNum < THREAD_COUNT; threadNum++) {

            int finalThreadNum = threadNum;
            tasksIJ.get(finalThreadNum).forEach(i -> {
                tasksIJ.get(finalThreadNum).forEach(j -> {
                    executorService.submit(() -> calculateIJMult(i, j, result, matrices, size));
                });
            });
        }
        executorService.shutdown();

        List<List<Integer>> resultList = new ArrayList<>();

        while (!executorService.isTerminated()) {

        }

        for (int i = 0; i < size; i++) {
            resultList.add(Arrays.stream(result[i]).boxed().toList());
        }

        return resultList;
    }

    void calculateIJMult(int i, int j, int[][] result, MatricesDTO matrices, int size) {

        for (var k = 0; k < size; k++) {
            result[i][j] += matrices.getMatrix1().get(i).get(j) * matrices.getMatrix2().get(k).get(j);
        }

    }
}
