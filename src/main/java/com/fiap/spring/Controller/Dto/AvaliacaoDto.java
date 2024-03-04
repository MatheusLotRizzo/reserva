package com.fiap.spring.Controller.Dto;


public record AvaliacaoDto(
        String emailUsuario,
        String cnpjRestaurante,
        int pontuacao,
        String comentario
) {
}
