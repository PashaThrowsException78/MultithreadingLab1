package com.example.multiplication;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MultiplicationService {

    private final InputClient inputClient;

    public MultiplicationService(InputClient inputClient) {
        this.inputClient = inputClient;
    }

    public List<List<Integer>> multiplicate(int size) {

        MatricesDTO matrices = inputClient.getMatrices(size);

        int[][] result = new int[size][size];

        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                for (var k = 0; k < size; k++) {
                    result[i][j] += matrices.getMatrix1().get(i).get(j) * matrices.getMatrix2().get(k).get(j);
                }
            }
        }

        List<List<Integer>> resultList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            resultList.add(Arrays.stream(result[i]).boxed().toList());
        }

        return resultList;
    }
}
