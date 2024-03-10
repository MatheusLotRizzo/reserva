package com.fiap.spring.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.reserva.application.controller.ReservaControllerApplication;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.spring.Controller.Dto.ReservaDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reserva", description = "Reserva do usuário para um restaurante")
@RestController
@RequestMapping("/reserva")
public class ReservaControllerSpring {

    private ReservaControllerApplication reservaController;

    @Operation(summary = "Cria uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody ReservaDto reservaDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaController.cadastrarReserva(reservaDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Altera uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PutMapping
    public ResponseEntity<?> alterarReserva(@RequestBody ReservaDto reservaDto ){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaController.alterarReserva(reservaDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Cancela uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PatchMapping("/cancelar")
    public ResponseEntity<?> cancelarReserva(@RequestBody ReservaDto reservaDto ){
        try {
        	reservaController.cancelarReserva(reservaDto);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/concluir")
    public ResponseEntity<?> concluirReserva(@RequestBody ReservaDto reservaDto ){
        try {
        	reservaController.concluirReserva(reservaDto);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Operation(summary = "Busca reserva")
    @ApiOperation("Busca reserva por email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping("/{email}")
    public ResponseEntity<?> buscarTodasReservaDoUsuarioPeloEmail(@PathVariable @ApiParam(value = "Email do usuario", example = "exemplo@dominio.com.br")
                                                                      String email) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    reservaController.getBuscarTodasReservaDoUsuarioPeloEmail(email)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Busca reserva")
    @ApiOperation("Busca reserva por cnpj")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarTodasReservaDoUsuarioPeloCnpj(@PathVariable @ApiParam(value = "Cnpj", example = "11 caracteres alfanumericos")
                                                                     String cnpj){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    reservaController.getBuscarTodasRerservasRestaurantePeloCNPJ(cnpj)
            );
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Busca reserva")
    @ApiOperation("Busca reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping
    public ResponseEntity<?> buscarTodasReservas(@RequestBody ReservaDto reservaDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    reservaController.getBuscarTodas(reservaDto)
            );
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
