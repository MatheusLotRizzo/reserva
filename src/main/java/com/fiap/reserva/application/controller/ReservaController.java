package com.fiap.reserva.application.controller;

import com.fiap.reserva.adapter.ReservaAdapter;
import com.fiap.reserva.application.usecase.CadastrarReserva;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.infra.ReservaEmMemoria;

public class ReservaController {

    private final ReservaRepository repository;
    private final ReservaAdapter adapter;
    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;

    public ReservaController() {
        this.usuarioRepository = null;
        this.restauranteRepository = null;
        this.adapter = new ReservaAdapter(restauranteRepository,usuarioRepository);
        this.repository = new ReservaEmMemoria();
    }

    public void cadastrar(ReservaDto reservaDto){
        new CadastrarReserva(repository).executar(this.adapter.toReserva(reservaDto));
    }
}
