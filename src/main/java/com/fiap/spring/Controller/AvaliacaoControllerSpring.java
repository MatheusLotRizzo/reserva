package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.AvaliacaoController;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoControllerSpring {

    private AvaliacaoController avaliacaoController;

    @PostMapping
    public ResponseEntity<?> avaliar(@RequestBody AvaliacaoDto avaliacaoDto){
        try {
            avaliacaoController.avaliar(avaliacaoDto.emailUsuario(),avaliacaoDto.cnpjRestaurante(),avaliacaoDto.pontuacao(),avaliacaoDto.comentario());
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
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
