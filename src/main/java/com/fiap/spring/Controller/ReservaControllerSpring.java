package com.fiap.spring.Controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.reserva.application.controller.ReservaControllerApplication;
import com.fiap.spring.Controller.Dto.CriarReservaDTO;
import com.fiap.spring.Controller.Dto.ReservaDto;
import com.fiap.spring.infra.Utils;

import io.swagger.annotations.ApiOperation;
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
	
	@Autowired
    private ReservaControllerApplication reservaController;

	@Operation(summary = "Cria uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody CriarReservaDTO reservaDto){
        return Utils.response(HttpStatus.CREATED, 
    		() -> reservaController.criarReserva(reservaDto.toReservaDTO()));
    }

    @Operation(summary = "Cancela uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @PatchMapping("/cancelar/{numeroReserva}")
    public ResponseEntity<?> cancelarReserva(@PathVariable final UUID numeroReserva){
        return Utils.response(HttpStatus.NO_CONTENT,
			() -> {
				reservaController.cancelarReserva(numeroReserva); 
				return null;
			});
    }

    @PatchMapping("/concluir/{numeroReserva}")
    public ResponseEntity<?> concluirReserva(@PathVariable("numeroReserva") UUID numeroReserva){
        return Utils.response(HttpStatus.NO_CONTENT,
			() -> {
				reservaController.concluirReserva(numeroReserva); 
				return null;
			});
    }

    @Operation(summary = "Busca reserva")
    @ApiOperation("Busca reserva por email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping("/usuario/{email}")
    public ResponseEntity<?> buscarReservasDoUsuarioPeloEmail(@PathVariable String email) {
		return Utils.response(HttpStatus.OK,
			() -> reservaController.getBuscarReservasDoUsuarioPeloEmail(email));
    }
    
    
    
    @GetMapping("/usuario/situacao")
    public ResponseEntity<?> buscarReservasDoUsuarioPelaSituacao(@RequestBody ReservaDto reservaDto) {
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservasDoUsuarioPelaSituacao(reservaDto));
    }

    @Operation(summary = "Busca reserva")
    @ApiOperation("Busca reserva por cnpj")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDto.class, description = "Reserva")) }),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
    })
    @GetMapping("/restaurante/{cnpj}")
    public ResponseEntity<?> buscarReservasDoRestaurantePeloCnpj(@PathVariable("cnpj") String cnpj){
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarTodasRerservasRestaurantePeloCNPJ(cnpj));
    }
    
    @GetMapping("/restaurante/situacao")
    public ResponseEntity<?> buscarReservasDoRestaurantePorSituacao(@RequestBody ReservaDto reservaDto){
        return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservasDoRestaurantePorSituacao(reservaDto));
    }
    
    @GetMapping("/{numeroReserva}")
    public ResponseEntity<?> buscarReservaDoRestaurantePeloNumeroReserva(@PathVariable UUID numeroReserva){
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservaPeloNumeroReserva(numeroReserva));
    }
    
    @GetMapping("/restaurante/{cnpj}/{data}")
    public ResponseEntity<?> buscarReservaDoRestaurantePelaData(@PathVariable("cnpj") String cnpjVo,@PathVariable LocalDate data){
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservaDoRestaurantePeloData(cnpjVo, data));
    }
}