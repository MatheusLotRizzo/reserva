package com.fiap.reserva.application.service;

import com.fiap.reserva.domain.entity.*;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;
import com.fiap.spring.Controller.Dto.UsuarioDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
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
    @Mock
    private UsuarioService usuarioService;
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
        assertEquals("Restaurante não foi encontrado!", throwable.getMessage());
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
        when(repository.buscarTodasPor(any(Restaurante.class)))
                .thenReturn(Arrays.asList(avaliacao1,avaliacao2));

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
        when(repository.buscarTodasPor(any(Restaurante.class))).thenReturn(null);

        // Act
        final List<Avaliacao> avaliacoesArmazenadas = service.getBuscarTodasAvaliacoesRestaurantePeloCNPJ(cnpj);
        // Assert
        assertThat(avaliacoesArmazenadas).isNull();
        verify(repository, times(1)).buscarTodasPor(any(Restaurante.class));
    }

    @Test
    void deveCadastrarAvaliacao() throws BusinessException {
        final AvaliacaoDto avaliacaoDto = new AvaliacaoDto (
                "teste_avaliacao@fiap.com.br",
                "94690811000105",
                5,
                "sujinho restaurante melhor experiencia em são paulo"
        ) ;

        final RestauranteDto restauranteMock = new RestauranteDto(
                "94690811000105",
                "Sujinho Restaurante",
                5,
                TipoCozinha.JAPONESA,
                Collections.emptyList(),
                null);

        final UsuarioDto usuarioMock = new UsuarioDto(
                "Usuario Teste",
                "teste_avaliacao@fiap.com.br",
                "11 99999-8888"
        );

        when(restauranteService.getBuscarPor(any())).thenReturn(restauranteMock.toEntity());
        when(usuarioService.getBuscarPor(any())).thenReturn(usuarioMock.toEntity());
        when(repository.avaliar(any())).thenReturn(avaliacaoDto.toEntity());
        //Act
        final Avaliacao avaliacaoArmazenada = service.avaliar(avaliacaoDto.toEntity());

        //Assert
        assertNotNull(avaliacaoArmazenada);
        verify(repository, times(1)).avaliar(any(Avaliacao.class));
    }

    @Test
    void naoDeveCadastrarAvaliacao() throws BusinessException {
        //Arrange // Act // Assert
        final Throwable throwable = assertThrows(BusinessException.class, () -> service.avaliar(null));
        assertEquals("Avaliacao é obrigatorio", throwable.getMessage());
    }
}
