package com.fiap.reserva.application.usecase.avaliacao;

import com.fiap.reserva.domain.entity.*;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeixarAvaliacaoTest {

    @Mock
    private AvaliacaoRepository repository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void deveCadastrarAvaliacao() throws BusinessException {
        //Arrange
        final Avaliacao avaliacao = new Avaliacao(
                new Usuario("teste_avaliacao@fiap.com.br"),
                new Restaurante("94690811000105"),
                5,
                "sujinho restaurante melhor experiencia em são paulo"
        ) ;
        when(repository.avaliar(avaliacao)).thenReturn(avaliacao);
        //Act
        final Avaliacao avaliacaoArmazenada = new DeixarAvaliacao(repository).executar(avaliacao);
        //Assert
        assertThat(avaliacaoArmazenada)
                .isInstanceOf(Avaliacao.class)
                .isNotNull();
        assertThat(avaliacaoArmazenada.getUsuario())
                .isEqualTo(avaliacao.getUsuario());
        assertThat(avaliacaoArmazenada.getRestaurante())
                .isEqualTo(avaliacao.getRestaurante());
        assertThat(avaliacaoArmazenada.getPontuacao())
                .isEqualTo(avaliacao.getPontuacao());
        assertThat(avaliacaoArmazenada.getComentario())
                .isNotNull();
        verify(repository, times(1)).avaliar(avaliacao);
    }

    @Test
    void naoDeveCadastrarAvaliacao() throws BusinessException {
        //Arrange // Act // Assert
        final Throwable throwable = assertThrows(BusinessException.class, () -> new DeixarAvaliacao(repository).executar(null));
        assertEquals("Avaliacao é obrigatorio", throwable.getMessage());
    }
}
