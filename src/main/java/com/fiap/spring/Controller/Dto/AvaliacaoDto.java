package com.fiap.spring.Controller.Dto;

import java.time.LocalDateTime;

public record AvaliacaoDto(
        String emailUsuario,
        String cnpjRestaurante,
        int pontuacao,
        String comentario
) {
}
