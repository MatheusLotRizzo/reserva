package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.RestauranteController;
import com.fiap.reserva.application.controller.UsuarioController;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.spring.Controller.Dto.ReservaDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;
import com.fiap.spring.Controller.Dto.UsuarioDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante")
public class RestauranteControllerSpring {

    private RestauranteController restauranteController;
    @PostMapping
    public ResponseEntity<?> criarRestaurante(@RequestBody RestauranteDto restauranteDto){
        try {
            restauranteController.cadastrar(
                restauranteDto.cnpj(),
                restauranteDto.nome(),
                restauranteDto.capacidade(),
                restauranteDto.tipoCozinha(),
                restauranteDto.horarioAbertura(),
                restauranteDto.horarioEncerramento(),
                restauranteDto.cep(),
                restauranteDto.logradouro(),
                restauranteDto.numero(),
                restauranteDto.complemento(),
                restauranteDto.bairro(),
                restauranteDto.cidade(),
                restauranteDto.estado()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> alterarRestaurante(@RequestBody RestauranteDto restauranteDto ){
        try {
            restauranteController.alterar(
                    restauranteDto.cnpj(),
                    restauranteDto.nome(),
                    restauranteDto.capacidade(),
                    restauranteDto.tipoCozinha(),
                    restauranteDto.horarioAbertura(),
                    restauranteDto.horarioEncerramento(),
                    restauranteDto.cep(),
                    restauranteDto.logradouro(),
                    restauranteDto.numero(),
                    restauranteDto.complemento(),
                    restauranteDto.bairro(),
                    restauranteDto.cidade(),
                    restauranteDto.estado()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
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
