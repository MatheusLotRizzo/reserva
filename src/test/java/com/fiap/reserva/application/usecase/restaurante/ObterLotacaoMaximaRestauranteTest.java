package com.fiap.reserva.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

class ObterLotacaoMaximaRestauranteTest {

    @Mock
    private RestauranteRepository repository;

    @InjectMocks
    private ObterLotacaoMaximaRestaurante obterLotacaoMaximaRestaurante;

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
    void deveLancarExcecaoQuandoRestauranteNulo() {
        final Throwable throwable = assertThrows(BusinessException.class,
                () -> obterLotacaoMaximaRestaurante.executar(null));
        assertEquals("Restaurante é obrigatorio", throwable.getMessage());
    }

    @Test
    void deveRetornarLotacaoMaximaQuandoRestauranteValido() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");
        Integer lotacaoEsperada = 100;

        when(repository.obterLotacaoMaximaRestaurante(restaurante)).thenReturn(lotacaoEsperada);

        Integer lotacaoObtida = obterLotacaoMaximaRestaurante.executar(restaurante);

        assertNotNull(lotacaoObtida);
        assertEquals(lotacaoEsperada, lotacaoObtida);
        verify(repository).obterLotacaoMaximaRestaurante(restaurante);
    }
}
