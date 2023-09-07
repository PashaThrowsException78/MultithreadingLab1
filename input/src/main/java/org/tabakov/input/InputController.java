package org.tabakov.input;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {

    private final MatrixGenerator matrixGenerator;

    public InputController(MatrixGenerator matrixGenerator) {
        this.matrixGenerator = matrixGenerator;
    }

    @GetMapping("/matrices/{size}")
    public ResponseEntity<?> getMatrices(@PathVariable @NonNull Integer size) {
        MatricesDTO matrices = new MatricesDTO(
                matrixGenerator.generate(size),
                matrixGenerator.generate(size)
        );

        return ResponseEntity.ok(matrices);
    }
}
