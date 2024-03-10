package com.fiap.reserva.domain.vo;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailVoTest {

    @Test
    void deveDarErroComEmailInvalido() {
        assertThrows(BusinessException.class, () -> new EmailVo(null));
        assertThrows(BusinessException.class, () -> new EmailVo(""));
        assertThrows(BusinessException.class, () -> new EmailVo("matheus.com"));
        assertThrows(BusinessException.class, () -> new EmailVo("matheus.com.br"));
        assertThrows(BusinessException.class, () -> new EmailVo("111111@#@.com.br"));
        assertThrows(BusinessException.class, () -> new Usuario("Matheus", "_________@.com.br"));
        assertThrows(BusinessException.class, () -> new Usuario("Matheus", "---------@.com.br"));
        assertThrows(BusinessException.class, () -> new Usuario("Matheus", "@.com.br"));
        assertThrows(BusinessException.class, () -> new Usuario("Matheus", "@@@@.com.br"));
    }

    @Test
    void deveCriarEmailVoComEmailValido() throws BusinessException {
        assertNotNull(new EmailVo("matheus@gmail.com"));
        assertNotNull(new EmailVo("matheus2024@gmail.com.br"));
        assertNotNull(new EmailVo("matheus_rizzo@gmail.com.br"));
        assertNotNull(new EmailVo("matheus.rizzo@gmail.com.br"));
    }
}