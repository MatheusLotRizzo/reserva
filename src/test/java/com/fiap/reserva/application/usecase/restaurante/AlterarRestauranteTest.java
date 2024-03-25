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
    void deveAlterarRestauranteExistente() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restauranteAlterado = new Restaurante(cnpj, "Restaurante Alterado");

        when(repository.alterar(restauranteAlterado)).thenReturn(restauranteAlterado);

        Restaurante resultado = alterarRestaurante.executar(restauranteAlterado);

        assertNotNull(resultado);
        assertEquals(restauranteAlterado, resultado);
        verify(repository).alterar(restauranteAlterado);
    }
}
