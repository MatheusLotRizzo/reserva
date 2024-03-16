package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AvaliacaoTest {

    @Test
    void naoDeveSalvarAvaliacaoUsuarioNaoInformado() throws BusinessException {
        //Arrange
        final Throwable throwable = assertThrows(BusinessException.class, () ->
                new Avaliacao(
                        null,
                        new Restaurante("94690811000105"),
                        5,
                        "comentario"
                        ));

        // Act e Assert
        assertEquals("Usuario é obrigatório", throwable.getMessage());
    }

    @Test
    void naoDeveSalvarAvaliacaoRestauranteNaoInformado() throws BusinessException {
        //Arrange
        final Throwable throwable = assertThrows(BusinessException.class, () ->
                new Avaliacao(
                        new Usuario("teste_avaliacao@fiap.com.br"),
                        null,
                        5,
                        "comentario"
                ));

        // Act e Assert
        assertEquals("Restaurante é obrigatório", throwable.getMessage());
    }

    @Test
    void naoDeveSalvarAvaliacaoPontuacaoForaDoRange() throws BusinessException {
        //Arrange
        final Throwable throwable = assertThrows(BusinessException.class, () ->
                new Avaliacao(
                        new Usuario("teste_avaliacao@fiap.com.br"),
                        new Restaurante("94690811000105"),
                        6,
                        "comentario"
                ));

        // Act e Assert
        assertEquals("Valor inválido para a pontuação. É considerado valor válido os valores entre 0 e 5", throwable.getMessage());
    }

    @Test
    void naoDeveSalvarAvaliacaoComentarioNaoInformada() throws BusinessException {
        //Arrange
        final Throwable throwable = assertThrows(BusinessException.class, () ->
                new Avaliacao(
                        new Usuario("teste_avaliacao@fiap.com.br"),
                        new Restaurante("94690811000105"),
                        6,
                        null
                ));

        // Act e Assert
        assertEquals("Comentário é obrigatório", throwable.getMessage());
    }

    @Test
    void naoDeveSalvarAvaliacaoComentarioVazio() throws BusinessException {
        //Arrange
        final Throwable throwable = assertThrows(BusinessException.class, () ->
                new Avaliacao(
                        new Usuario("teste_avaliacao@fiap.com.br"),
                        new Restaurante("94690811000105"),
                        6,
                        ""
                ));

        // Act e Assert
        assertEquals("Comentário é obrigatório", throwable.getMessage());
    }

}
