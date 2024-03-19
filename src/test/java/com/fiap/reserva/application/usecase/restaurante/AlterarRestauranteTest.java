package com.fiap.reserva.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
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
        AlterarRestaurante alterarRestaurante = new AlterarRestaurante(repository);

        when(repository.buscarPorCnpj(cnpj)).thenReturn(null);

        final Throwable throwable = assertThrows(EntidadeNaoEncontrada.class, () -> alterarRestaurante.executar(restaurante));

        assertEquals("Restaurante não pode ser alterado, pois não foi encontrado", throwable.getMessage());
        verify(repository).buscarPorCnpj(cnpj);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveAlterarRestauranteExistente() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restauranteExistente = new Restaurante(cnpj, "Restaurante Existente");
        Restaurante restauranteAlterado = new Restaurante(cnpj, "Restaurante Alterado");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(restauranteExistente);
        when(repository.alterar(restauranteAlterado)).thenReturn(restauranteAlterado);

        Restaurante resultado = new AlterarRestaurante(repository).executar(restauranteAlterado);

        assertNotNull(resultado);
        assertEquals(restauranteAlterado, resultado);
        verify(repository).buscarPorCnpj(cnpj);
        verify(repository).alterar(restauranteAlterado);
    }
}
