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
    public ResponseEntity<?> criarReserva(@RequestBody ReservaDto reservaDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
            		.body(reservaController.criarReserva(reservaDto));
        } catch(BusinessException ex) {
        	return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
        				.body(MessageErrorHandler.create(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(MessageErrorHandler.create(ex.getMessage()));
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
    @PatchMapping("/cancelar/{numeroReserva}")
    public ResponseEntity<?> cancelarReserva(@PathVariable final UUID numeroReserva){
        try {
        	reservaController.cancelarReserva(numeroReserva);
            return ResponseEntity.noContent().build();
        }catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
	    }
    }

    @PatchMapping("/concluir/{numeroReserva}")
    public ResponseEntity<?> concluirReserva(@PathVariable("numeroReserva") UUID numeroReserva){
        try {
        	reservaController.concluirReserva(numeroReserva);
            return ResponseEntity.noContent().build();
        } catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
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
    @GetMapping("/usuario/{email}")
    public ResponseEntity<?> buscarReservasDoUsuarioPeloEmail(
		@PathVariable 
		@ApiParam(value = "Email do usuario", example = "exemplo@dominio.com.br")
        String email
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                reservaController.getBuscarReservasDoUsuarioPeloEmail(email)
            );
        } catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
	    }
    }
    
    @GetMapping("/usuario/situacao")
    public ResponseEntity<?> buscarReservasDoUsuarioPelaSituacao(@RequestBody ReservaDto reservaDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                reservaController.getBuscarReservasDoUsuarioPelaSituacao(reservaDto)
            );
        } catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
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
    @GetMapping("/restaurante/{cnpj}")
    public ResponseEntity<?> buscarReservasDoRestaurantePeloCnpj(
		@PathVariable("cnpj") 
		@ApiParam(value = "cnpj", example = "11 caracteres alfanumericos")
		String cnpj
	){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                reservaController.getBuscarTodasRerservasRestaurantePeloCNPJ(cnpj)
            );
        }catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
	    }
    }
    
    @GetMapping("/restaurante/situacao")
    public ResponseEntity<?> buscarReservasDoRestaurantePorSituacao(@RequestBody ReservaDto reservaDto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                reservaController.getBuscarReservasDoRestaurantePorSituacao(reservaDto)
            );
        }catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
	    }
    }
    
    @GetMapping("/{numeroReserva}")
    public ResponseEntity<?> buscarReservaDoRestaurantePeloNumeroReserva(@PathVariable UUID numeroReserva){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                reservaController.getBuscarReservaPeloNumeroReserva(numeroReserva)
            );
        }catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
	    }
    }
    
    @GetMapping("/restaurante/{cnpj}/{data}")
    public ResponseEntity<?> buscarReservaDoRestaurantePelaData(@PathVariable("cnpj") String cnpjVo,@PathVariable LocalDate data){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                reservaController.getBuscarReservaDoRestaurantePeloData(cnpjVo, data)
            );
        }catch(BusinessException ex) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(MessageErrorHandler.create(ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageErrorHandler.create(ex.getMessage()));
	    }
    }

}
