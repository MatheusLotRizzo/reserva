package com.fiap.reserva.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void naoDeveCriarUsuarioComEmailVazio() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", null));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", ""));
        assertEquals("E-mail inválido", throwable.getMessage());
    }

    @Test
    void naoDeveCriarUsuarioComEmailTendoApenasComCaracteresEspeciais(){
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("_________@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("---------@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("@@@@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());
    }

    @Test
    void deveCriarUsuarioComEmailValido() {
        Usuario usuario = new Usuario("matheus@gmail.com");
        assertNotNull(usuario);

        usuario = new Usuario("matheus_rizzo@gmail.com.br");
        assertNotNull(usuario);
    }
}