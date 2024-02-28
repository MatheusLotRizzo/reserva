package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.ReservaController;
import com.fiap.reserva.application.controller.UsuarioController;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.spring.Controller.Dto.ReservaDto;
import com.fiap.spring.Controller.Dto.UsuarioDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserva")
public class UsuarioControllerSpring {

    private UsuarioController usuarioController;
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDto usuarioDto){
        try {
            usuarioController.cadastrar(usuarioDto.nome(),usuarioDto.email(),usuarioDto.celular());
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> alterarUsuario(@RequestBody UsuarioDto usuarioDto ){
        try {
            usuarioController.alterar(usuarioDto.nome(),usuarioDto.email(),usuarioDto.celular());
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> excluirUsuario(@RequestBody UsuarioDto usuarioDto ){
        try{
            usuarioController.excluir(usuarioDto.email());
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable UsuarioDto usuarioDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    usuarioController.getBuscarPor(usuarioDto.nome(),usuarioDto.email(),usuarioDto.celular())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String cnpj){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    usuarioController.getBuscarPorEmail(cnpj)
            );
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
