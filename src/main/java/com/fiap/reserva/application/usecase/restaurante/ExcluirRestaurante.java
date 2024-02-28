package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

public class ExcluirRestaurante {
    private final RestauranteRepository repository;

    public ExcluirRestaurante(RestauranteRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public void executar(final String cnpj){
        repository.excluir(new CnpjVo(cnpj));
    }
}
