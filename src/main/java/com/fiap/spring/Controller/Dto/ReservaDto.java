package com.fiap.spring.Controller.Dto;

import java.time.LocalDateTime;

public record ReservaDto(
        String emailUsuario,
        String cnpjRestaurante,
        LocalDateTime dataHora,
        int quantidadeLugares,
        String status
) {
}
