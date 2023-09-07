package com.example.output;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OutputController {

    private final MatrixConsoleWriter matrixConsoleWriter;
    private final OutputClient outputClient;

    public OutputController(MatrixConsoleWriter matrixConsoleWriter, OutputClient outputClient) {
        this.matrixConsoleWriter = matrixConsoleWriter;
        this.outputClient = outputClient;
    }

    @GetMapping("/calculated/{size}")
    public ResponseEntity<?> write(@PathVariable @NonNull Integer size) {

        List<List<Integer>> matrix = outputClient.getMultiplicated(size);
        matrixConsoleWriter.write(matrix);

        return ResponseEntity.ok(matrix);
    }
}
