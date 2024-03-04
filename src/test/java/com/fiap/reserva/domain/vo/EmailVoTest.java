package com.fiap.reserva.domain.vo;

import com.fiap.reserva.domain.entity.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailVoTest {

    @Test
    void deveDarErroComEmailInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new EmailVo(null));
        assertThrows(IllegalArgumentException.class, () -> new EmailVo(""));
        assertThrows(IllegalArgumentException.class, () -> new EmailVo("matheus.com"));
        assertThrows(IllegalArgumentException.class, () -> new EmailVo("matheus.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new EmailVo("111111@#@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "_________@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "---------@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "@@@@.com.br"));
    }

    @Test
    void deveCriarEmailVoComEmailValido() {
        assertNotNull(new EmailVo("matheus@gmail.com"));
        assertNotNull(new EmailVo("matheus2024@gmail.com.br"));
        assertNotNull(new EmailVo("matheus_rizzo@gmail.com.br"));
        assertNotNull(new EmailVo("matheus.rizzo@gmail.com.br"));
    }
}