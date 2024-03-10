package com.fiap.spring.Controller;

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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario", description = "Usuario que fara a reserva")
@RestController
@RequestMapping("/usuario")
public class UsuarioControllerSpring {

	@Autowired
    private UsuarioControllerApplication usuarioController;

    @Operation(summary = "Cria um usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class, description = "Usuario")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDto usuarioDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioController.cadastrar(usuarioDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Altera um usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class, description = "Usuario")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PutMapping
    public ResponseEntity<?> alterarUsuario(@RequestBody UsuarioDto usuarioDto ){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioController.alterar(usuarioDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Deleta um usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class, description = "Usuario")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<?> excluirUsuario(@PathVariable
                                                @ApiParam(value = "Email", example = "exemplo@dominio.com.br")
                                                String email ){
        try{
            usuarioController.excluir(email);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Busca usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class, description = "Usuario")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping
    public ResponseEntity<?> buscarPorUsuario( @PathVariable UsuarioDto usuarioDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    usuarioController.getBuscarPor(usuarioDto)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
