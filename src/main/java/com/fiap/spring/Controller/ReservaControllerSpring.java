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
import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.spring.Controller.Dto.CriarReservaDTO;
import com.fiap.spring.infra.Utils;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerCreate;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerNoContent;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerOk;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reserva", description = "Reserva do usuário para um restaurante")
@RestController
@RequestMapping("/reserva")
public class ReservaControllerSpring {
	
	@Autowired
    private ReservaControllerApplication reservaController;

	@PostMapping
	@Operation(summary = "Cria reserva")
	@ApiResponseSwaggerCreate
    public ResponseEntity<?> criarReserva(@RequestBody CriarReservaDTO reservaDto){
        return Utils.response(HttpStatus.CREATED, 
    		() -> reservaController.criarReserva(reservaDto.toReservaDTO()));
    }

	@PatchMapping("/cancelar/{numeroReserva}")
    @Operation(summary = "Cancela reserva")
    @ApiResponseSwaggerNoContent
    public ResponseEntity<?> cancelarReserva(@PathVariable final UUID numeroReserva){
        return Utils.response(HttpStatus.NO_CONTENT,
			() -> {
				reservaController.cancelarReserva(numeroReserva); 
				return null;
			});
    }

    @PatchMapping("/concluir/{numeroReserva}")
    @Operation(summary = "Conclui reserva")
    @ApiResponseSwaggerNoContent
    public ResponseEntity<?> concluirReserva(@PathVariable("numeroReserva") UUID numeroReserva){
        return Utils.response(HttpStatus.NO_CONTENT,
			() -> {
				reservaController.concluirReserva(numeroReserva); 
				return null;
			});
    }

    @GetMapping("/usuario/{email}")
    @Operation(summary = "Busca reserva pelo email.")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarReservasDoUsuarioPeloEmail(@PathVariable String email) {
		return Utils.response(HttpStatus.OK,
			() -> reservaController.getBuscarReservasDoUsuarioPeloEmail(email));
    }
    
    @GetMapping("/usuario/{email}/{situacao-reserva}")
    @Operation(summary = "Busca reservas de um usuario por email e situação da reserva")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarReservasDoUsuarioPelaSituacao(@PathVariable("email")String email, @PathVariable("situacao-reserva") SituacaoReserva situacaoReserva) {
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservasDoUsuarioPelaSituacao(email, situacaoReserva));
    }
    
    @GetMapping("/restaurante/{cnpj}")
    @Operation(summary = "Busca reservas do restaurante por cnpj")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarReservasDoRestaurantePeloCnpj(@PathVariable("cnpj") String cnpj){
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarTodasRerservasRestaurantePeloCNPJ(cnpj));
    }
    
    @GetMapping("/restaurante/{cnpj}/situacao/{situacao-reserva}")
    @Operation(summary = "Busca reservas do restaurante por cnpj e situação da reserva")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarReservasDoRestaurantePorSituacao(@PathVariable("cnpj")String cnpj, @PathVariable("situacao-reserva") SituacaoReserva situacaoReserva){
        return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservasDoRestaurantePorSituacao(cnpj, situacaoReserva));
    }
    
    @GetMapping("/{numeroReserva}")
    @Operation(summary = "Busca reserva do restaurante por numero da reserva")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarReservaDoRestaurantePeloNumeroReserva(@PathVariable UUID numeroReserva){
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservaPeloNumeroReserva(numeroReserva));
    }
    
    @GetMapping("/restaurante/{cnpj}/{data}")
    @Operation(summary = "Busca reservas do restaurante pelo cnpj e data")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarReservaDoRestaurantePelaData(@PathVariable("cnpj") String cnpjVo,@PathVariable LocalDate data){
    	return Utils.response(HttpStatus.OK, 
			() -> reservaController.getBuscarReservaDoRestaurantePeloData(cnpjVo, data));
    }
}