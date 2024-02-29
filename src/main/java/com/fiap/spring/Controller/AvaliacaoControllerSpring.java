package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.AvaliacaoControllerApplication;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoControllerSpring {

    private AvaliacaoControllerApplication avaliacaoController;

    @PostMapping
    public ResponseEntity<?> avaliar(@RequestBody AvaliacaoDto avaliacaoDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoController.avaliar(avaliacaoDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarTodasPorCnpj(@PathVariable String cnpj) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    avaliacaoController.getBuscarTodos(cnpj)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
