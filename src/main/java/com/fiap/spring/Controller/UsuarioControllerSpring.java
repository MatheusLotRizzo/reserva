package com.fiap.spring.Controller;

import com.fiap.spring.infra.Utils;
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
            @ApiResponse(responseCode = "400", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
    })
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDto usuarioDto){
        return Utils.response(HttpStatus.CREATED, () -> usuarioController.cadastrar(usuarioDto));
    }

    @Operation(summary = "Altera um usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class, description = "Usuario")) }),
            @ApiResponse(responseCode = "400", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
    })
    @PutMapping
    public ResponseEntity<?> alterarUsuario(@RequestBody UsuarioDto usuarioDto ){
        return Utils.response(HttpStatus.CREATED, () -> usuarioController.alterar(usuarioDto));
    }

    @Operation(summary = "Deleta um usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class, description = "Usuario")) }),
            @ApiResponse(responseCode = "400", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<?> excluirUsuario(@PathVariable @ApiParam(value = "Email", example = "exemplo@dominio.com.br") String email ){
        return Utils.response(HttpStatus.NO_CONTENT, () -> {
            usuarioController.excluir(email);
            return null;
        });
    }

    @Operation(summary = "Busca usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class, description = "Usuario")) }),
            @ApiResponse(responseCode = "400", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageErrorHandler.class)) }),
    })
    @GetMapping("/{email}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable @ApiParam(value = "Email", example = "exemplo@dominio.com.br") String email){
        return Utils.response(HttpStatus.OK, () -> usuarioController.getBuscarPor(email));
    }
}
