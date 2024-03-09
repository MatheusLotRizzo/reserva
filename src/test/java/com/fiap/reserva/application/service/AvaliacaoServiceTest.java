package com.fiap.reserva.application.service;

import com.fiap.reserva.domain.entity.*;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {
    @InjectMocks
    private AvaliacaoService service;
    @Mock
    private AvaliacaoRepository repository;
    @Mock
    private RestauranteService restauranteService;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void naoDeveBuscarAvaliacaoPorRestaurante() throws BusinessException {
        //Arrange // Act // Assert
        final Throwable throwable = assertThrows(BusinessException.class, () -> service.getBuscarTodasAvaliacoesRestaurantePeloCNPJ(null));
        assertEquals("Restaurante é obrigatorio para realizar a busca!", throwable.getMessage());
    }

    @Test
    void deveBuscarAvaliacaoPorRestaurante() throws BusinessException {
        //Arrange
        final CnpjVo cnpj = new CnpjVo("94690811000105");
        final Restaurante restaurante = mock(Restaurante.class);

        final Avaliacao avaliacao1 = new Avaliacao(
                new Usuario("teste_avaliacao@fiap.com.br"),
                new Restaurante("94690811000105"),
                5,
                "sujinho restaurante melhor experiencia em são paulo"
        ) ;
        final Avaliacao avaliacao2 = new Avaliacao(
                new Usuario("teste_avaliacao_2@fiap.com.br"),
                new Restaurante("94690811000105"),
                2,
                "paris 6 ja foi melhor"
        ) ;

        when(restauranteService.getBuscarPor(cnpj)).thenReturn(restaurante);
        when(service.getBuscarTodasAvaliacoesRestaurantePeloCNPJ(cnpj)).thenReturn(Arrays.asList(avaliacao1,avaliacao2));

        // Act
        final List<Avaliacao> avaliacoesArmazenadas = service.getBuscarTodasAvaliacoesRestaurantePeloCNPJ(cnpj);
        // Assert
        assertNotNull(avaliacoesArmazenadas);
        assertThatCollection(avaliacoesArmazenadas).hasSize(2);
        assertThatCollection(avaliacoesArmazenadas).filteredOnAssertions(avaliacao -> avaliacao.getRestaurante().equals(restaurante));

        verify(repository, times(1)).buscarTodasPor(any(Restaurante.class));

    }

    @Test
    void deveBuscarAvaliacaoPorRestaurante_QuandoNaoExistirRegistro() throws BusinessException {
        //Arrange
        final CnpjVo cnpj = new CnpjVo("94690811000105");
        final Restaurante restauranteMock = mock(Restaurante.class);
        when(restauranteService.getBuscarPor(cnpj)).thenReturn(restauranteMock);
        when(service.getBuscarTodasAvaliacoesRestaurantePeloCNPJ(cnpj)).thenReturn(null);

        // Act
        final List<Avaliacao> avaliacoesArmazenadas = service.getBuscarTodasAvaliacoesRestaurantePeloCNPJ(cnpj);
        // Assert
        assertThat(avaliacoesArmazenadas).isNull();
        verify(repository, times(1)).buscarTodasPor(any(Restaurante.class));
    }

    @Test
    void deveCadastrarAvaliacao() throws BusinessException {
        final Avaliacao avaliacao = new Avaliacao(
                new Usuario("teste_avaliacao@fiap.com.br"),
                new Restaurante("94690811000105"),
                5,
                "sujinho restaurante melhor experiencia em são paulo"
        ) ;

        when(service.avaliar(avaliacao)).thenReturn(avaliacao);
        //Act
        final Avaliacao avaliacaoArmazenada = service.avaliar(avaliacao);

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
        final Throwable throwable = assertThrows(BusinessException.class, () -> service.avaliar(null));
        assertEquals("Avaliacao é obrigatorio", throwable.getMessage());
    }
}
