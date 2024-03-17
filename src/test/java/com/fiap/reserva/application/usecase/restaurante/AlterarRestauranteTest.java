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

class AlterarRestauranteTest {

    @Mock
    private RestauranteRepository repository;

    @Mock
    private BuscarRestaurante buscarRestaurante;

    @InjectMocks
    private AlterarRestaurante alterarRestaurante;

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
    void naoDeveAlterarRestauranteEnviandoNull() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> alterarRestaurante.executar(null));
        assertEquals("Restaurante é obrigatorio", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void naoDeveAlterarRestauranteSeNaoEncontrado() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");
        when(buscarRestaurante.getRestaurantePor(cnpj)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> alterarRestaurante.executar(restaurante));
        assertEquals("Restaurante não pode ser alterado, pois não foi encontrado", throwable.getMessage());

        verify(buscarRestaurante).getRestaurantePor(cnpj);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveAlterarRestauranteComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");

        when(buscarRestaurante.getRestaurantePor(cnpj)).thenReturn(restaurante);

        when(repository.buscarPorCnpj(cnpj)).thenReturn(restaurante);
        when(repository.alterar(restaurante)).thenReturn(restaurante);

        Restaurante resultado = alterarRestaurante.executar(restaurante);

        assertEquals(restaurante, resultado);
        verify(buscarRestaurante).getRestaurantePor(cnpj);
        verify(repository).alterar(restaurante);
    }
}
