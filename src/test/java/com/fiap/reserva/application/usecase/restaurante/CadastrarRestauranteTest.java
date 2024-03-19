package com.fiap.reserva.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CadastrarRestauranteTest {

    @Mock
    private RestauranteRepository repository;

    @InjectMocks
    private CadastrarRestaurante cadastrarRestaurante;

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
    void naoDeveCadastrarRestauranteQuandoNulo() {
        final Throwable throwable = assertThrows(BusinessException.class,
                () -> cadastrarRestaurante.executar(null));

        assertEquals("Restaurante é obrigatório", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveCadastrarRestauranteComSucesso() throws BusinessException {
        Restaurante restaurante = new Restaurante(new CnpjVo("12345678901234"), "Restaurante Teste");

        when(repository.cadastrar(restaurante)).thenReturn(restaurante);

        Restaurante resultado = cadastrarRestaurante.executar(restaurante);

        assertNotNull(resultado);
        verify(repository).cadastrar(restaurante);
    }
}
