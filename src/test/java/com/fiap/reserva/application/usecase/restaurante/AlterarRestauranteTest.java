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

        when(repository.buscarPorCnpj(cnpj)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> alterarRestaurante.executar(restaurante));
        assertEquals("Restaurante não encontrado", throwable.getMessage());

        verify(repository).buscarPorCnpj(cnpj); // Verifica se a busca foi realmente feita
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveAlterarRestauranteComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(restaurante);
        when(repository.alterar(restaurante)).thenReturn(restaurante);

        Restaurante resultado = alterarRestaurante.executar(restaurante);

        assertEquals(restaurante, resultado);
        verify(repository).buscarPorCnpj(cnpj);
        verify(repository).alterar(restaurante);
    }
}
