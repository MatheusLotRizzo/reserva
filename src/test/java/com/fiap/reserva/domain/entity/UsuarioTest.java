package com.fiap.reserva.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioTest {

    @Test
    void deveDarErroComEmailInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", null));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", ""));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "matheus.com"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "matheus.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "111111@#@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "_________@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "---------@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "@.com.br"));
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "@@@@.com.br"));
    }

    @Test
    void deveCriarUsuarioComEmailValido() {
        assertNotNull(new Usuario("Matheus", "matheus@gmail.com"));
        assertNotNull(new Usuario("Matheus", "matheus2024@gmail.com.br"));
        assertNotNull(new Usuario("Matheus", "matheus_rizzo@gmail.com.br"));
        assertNotNull(new Usuario("Matheus", "matheus.rizzo@gmail.com.br"));
    }
}