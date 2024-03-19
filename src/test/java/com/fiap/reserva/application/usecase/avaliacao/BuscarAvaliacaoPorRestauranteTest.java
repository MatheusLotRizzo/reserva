package com.fiap.reserva.application.usecase.avaliacao;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BuscarAvaliacaoPorRestauranteTest {

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
    void naoDeveBuscarAvaliacaoPorRestaurante() throws BusinessException {
        //Arrange // Act // Assert
        final Throwable throwable = assertThrows(BusinessException.class, () -> new BuscarAvaliacaoPorRestaurante(repository).executar(null));
        assertEquals("Restaurante não foi encontrado!", throwable.getMessage());
    }

    @Test
    void deveBuscarAvaliacaoPorRestaurante() throws BusinessException {
        //Arrange
        final Restaurante restaurante = new Restaurante("94690811000105");
        final Avaliacao avaliacao1 = new Avaliacao(
                new Usuario("teste_avaliacao@fiap.com.br"),
                new Restaurante("94690811000105"),
                5,
                "sujinho restaurante melhor experiencia em são paulo"
        ) ;
        final Avaliacao avaliacao2 = new Avaliacao(
                new Usuario("teste_avaliacao_2@fiap.com.br"),
                new Restaurante("45861259000165"),
                2,
                "paris 6 ja foi melhor"
        ) ;

        when(repository.buscarTodasPor(any(Restaurante.class)))
                .thenReturn(Arrays.asList(avaliacao1,avaliacao2));

        // Act
        final List<Avaliacao> avaliacoesArmazenadas = new BuscarAvaliacaoPorRestaurante(repository).executar(restaurante);
        // Assert
        assertNotNull(avaliacoesArmazenadas);
        assertThatCollection(avaliacoesArmazenadas).hasSize(2);
        assertThatCollection(avaliacoesArmazenadas).filteredOnAssertions(avaliacao -> avaliacao.getRestaurante().equals(restaurante));

        verify(repository, times(1)).buscarTodasPor(any(Restaurante.class));
    }

    @Test
    void deveBuscarAvaliacaoPorRestaurante_QuandoNaoExistirRegistro() throws BusinessException {
        //Arrange
        final Restaurante restaurante = new Restaurante("94690811000105");

        when(repository.buscarTodasPor(any(Restaurante.class))).thenReturn(null);

        // Act
        final List<Avaliacao> avaliacoesArmazenadas = new BuscarAvaliacaoPorRestaurante(repository).executar(restaurante);
        // Assert
        assertThat(avaliacoesArmazenadas).isNull();
        verify(repository, times(1)).buscarTodasPor(any(Restaurante.class));
    }
}
