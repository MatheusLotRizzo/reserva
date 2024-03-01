package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.RestauranteControllerApplication;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.spring.Controller.Dto.RestauranteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurante", description = "Restaurante que recebera as reservas")
@RestController
@RequestMapping("/restaurante")
public class RestauranteControllerSpring {

    private RestauranteControllerApplication restauranteController;

    @Operation(summary = "Cria um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteDto.class, description = "Restaurante")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PostMapping
    public ResponseEntity<?> criarRestaurante(@RequestBody RestauranteDto restauranteDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteController.cadastrar(restauranteDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Altera um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteDto.class, description = "Restaurante")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PutMapping
    public ResponseEntity<?> alterarRestaurante(@RequestBody RestauranteDto restauranteDto ){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteController.alterar(restauranteDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Deleta um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteDto.class, description = "Restaurante")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @DeleteMapping("/{cnpj}")
    public ResponseEntity<?> excluirRestaurante(@PathVariable @ApiParam(value = "Cnpj", example = "11 caracteres alfanumericos")
                                                    String cnpj ){
        try{
            restauranteController.excluir(cnpj);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Busca restaurante")
    @ApiOperation("Busca restaurante por cnpj")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteDto.class, description = "Restaurante")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarPorCnpj(@PathVariable @ApiParam(value = "Cnpj", example = "11 caracteres alfanumericos")
                                               String cnpj) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    restauranteController.getBuscarPor(cnpj)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Busca restaurante")
    @ApiOperation("Busca restaurante por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteDto.class, description = "Restaurante")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping("/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable @ApiParam(value = "nome", example = "nome do restaurante")
                                               String nome) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    restauranteController.getBuscarPorNome(nome)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Busca restaurante")
    @ApiOperation("Busca restaurante por tipo de cozinha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteDto.class, description = "Restaurante")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping("/{tipoCozinha}")
    public ResponseEntity<?> buscarPorTipoCozinha(@PathVariable
                                                      @ApiParam(value = "Status da entidade", example = "ITALIANA",  allowableValues = "ITALIANA,JAPONESABRASILEIRA,FRANCESA,MEXICANA,VEGANA,VEGETARIANA")
                                                      String tipoCozinha) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    restauranteController.getBuscarPorTipoCozinha(tipoCozinha)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
