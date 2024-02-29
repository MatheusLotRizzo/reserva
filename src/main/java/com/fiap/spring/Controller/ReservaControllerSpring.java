package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.ReservaControllerApplication;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.spring.Controller.Dto.ReservaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserva")
public class ReservaControllerSpring {

    private ReservaControllerApplication reservaController;
    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody ReservaDto reservaDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaController.cadastrarReserva(reservaDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> alterarReserva(@RequestBody ReservaDto reservaDto ){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaController.alterarReserva(reservaDto));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> excluirReserva(@RequestBody ReservaDto reservaDto ){
        try{
            reservaController.excluirReserva(reservaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> buscarTodasReservaDoUsuarioPeloEmail(@PathVariable String email) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    reservaController.getBuscarTodasReservaDoUsuarioPeloEmail(email)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarTodasReservaDoUsuarioPeloCnpj(@PathVariable String cnpj){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    reservaController.getBuscarTodasRerservasRestaurantePeloCNPJ(cnpj)
            );
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
