package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.ReservaController;
import com.fiap.reserva.application.controller.ReservaDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ReservaControllerSpring {

    private ReservaController reservaController;
    @PostMapping("/")
    public void criarReserva(@RequestBody ReservaDto reservaDto){
        this.reservaController.cadastrar(reservaDto);
    }
}
