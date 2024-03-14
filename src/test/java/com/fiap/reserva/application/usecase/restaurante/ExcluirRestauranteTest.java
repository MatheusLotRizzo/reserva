package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ExcluirRestauranteTest {

    @Mock
    private RestauranteRepository repository;

    @InjectMocks
    private ExcluirRestaurante excluirRestaurante;

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
    void naoDeveExcluirRestauranteQuandoCnpjEhNulo() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> excluirRestaurante.executar(null));

        assertEquals("Restaurante é obrigatório", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveExcluirRestauranteComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        excluirRestaurante.executar(cnpj);

        verify(repository).excluir(cnpj);
        verifyNoMoreInteractions(repository);
    }
}
