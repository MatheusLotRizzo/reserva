package com.fiap.spring.Controller;

import com.fiap.spring.infra.Utils;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerCreate;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerNoContent;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerOk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.reserva.application.controller.UsuarioControllerApplication;
import com.fiap.spring.Controller.Dto.UsuarioDto;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario", description = "Usuario que fara a reserva")
@RestController
@RequestMapping("/usuario")
public class UsuarioControllerSpring {

	@Autowired
    private UsuarioControllerApplication usuarioController;

    @PostMapping
    @Operation(summary = "Cria um usuario")
    @ApiResponseSwaggerCreate
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDto usuarioDto){
        return Utils.response(HttpStatus.CREATED, () -> usuarioController.cadastrar(usuarioDto));
    }

    @PutMapping
    @Operation(summary = "Altera um usuario")
    @ApiResponseSwaggerCreate
    public ResponseEntity<?> alterarUsuario(@RequestBody UsuarioDto usuarioDto ){
        return Utils.response(HttpStatus.CREATED, () -> usuarioController.alterar(usuarioDto));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deleta um usuario")
    @ApiResponseSwaggerNoContent
    public ResponseEntity<?> excluirUsuario(@PathVariable @ApiParam(value = "Email", example = "exemplo@dominio.com.br") String email ){
        return Utils.response(HttpStatus.NO_CONTENT, () -> {
            usuarioController.excluir(email);
            return null;
        });
    }

    @GetMapping("/{email}")
    @Operation(summary = "Busca usuario")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarPorUsuario(@PathVariable @ApiParam(value = "Email", example = "exemplo@dominio.com.br") String email){
        return Utils.response(HttpStatus.OK, () -> usuarioController.getBuscarPor(email));
    }
}
