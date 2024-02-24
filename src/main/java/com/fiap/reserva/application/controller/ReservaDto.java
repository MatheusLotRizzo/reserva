package com.fiap.reserva.application.controller;

import java.time.LocalDateTime;

public record ReservaDto(
        String emailUsuario,
        String cnpjRestaurante,
        LocalDateTime dataHora,
        int quantidadeLugares,
        String status
) {
}
