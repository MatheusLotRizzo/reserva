package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ObterLotacaoMaximaRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private ObterLotacaoMaximaRestaurante obterLotacaoMaximaRestaurante;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() throws BusinessException {
        restaurante = new Restaurante(new CnpjVo("12345678901234"), "Restaurante Teste") ;
    }

    @Test
    void deveRetornarLotacaoMaximaQuandoRestauranteValido() throws BusinessException {
        // Configuração
        int lotacaoEsperada = 100;
        when(restauranteRepository.obterLotacaoMaximaRestaurante(restaurante)).thenReturn(lotacaoEsperada);

        // Ação
        int lotacaoObtida = obterLotacaoMaximaRestaurante.executar(restaurante);

        // Verificação
        assertEquals(lotacaoEsperada, lotacaoObtida, "A lotação máxima deve ser igual a lotação esperada.");
        verify(restauranteRepository).obterLotacaoMaximaRestaurante(restaurante);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNulo() {
        // Execução e Verificação
        assertThrows(BusinessException.class, () -> obterLotacaoMaximaRestaurante.executar(null), "Deve lançar BusinessException quando o restaurante for nulo.");
    }
}
