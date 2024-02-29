package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.RestauranteControllerApplication;
import com.fiap.spring.Controller.Dto.RestauranteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante")
public class RestauranteControllerSpring {

    private RestauranteControllerApplication restauranteController;
    @PostMapping
    public ResponseEntity<?> criarRestaurante(@RequestBody RestauranteDto restauranteDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteController.cadastrar(restauranteDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> alterarRestaurante(@RequestBody RestauranteDto restauranteDto ){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteController.alterar(restauranteDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<?> excluirRestaurante(@PathVariable String cnpj ){
        try{
            restauranteController.excluir(cnpj);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarPorCnpj(@PathVariable String cnpj) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    restauranteController.getBuscarPor(cnpj)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    restauranteController.getBuscarPorNome(nome)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{tipoCozinha}")
    public ResponseEntity<?> buscarPorTipoCozinha(@PathVariable String tipoCozinha) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    restauranteController.getBuscarPorTipoCozinha(tipoCozinha)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
