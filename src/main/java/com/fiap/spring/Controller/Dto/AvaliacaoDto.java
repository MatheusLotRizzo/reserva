package com.fiap.spring.Controller.Dto;


import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;

public record AvaliacaoDto(
        String emailUsuario,
        String cnpjRestaurante,
        int pontuacao,
        String comentario
) {
    public Avaliacao toEntity() throws BusinessException {
        return new Avaliacao(
                new Usuario(emailUsuario),
                new Restaurante(cnpjRestaurante),
                pontuacao,
                comentario
        );
    }
}
