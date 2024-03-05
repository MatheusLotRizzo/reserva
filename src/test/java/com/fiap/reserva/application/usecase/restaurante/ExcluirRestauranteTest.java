package com.fiap.reserva.application.usecase.restaurante;

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

@ExtendWith(MockitoExtension.class)
class ExcluirRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private ExcluirRestaurante excluirRestaurante;

    @BeforeEach
    void setUp() {
        // Com @Mock e @InjectMocks, os mocks são inicializados automaticamente,
        // então não há necessidade de inicialização explícita dentro deste método.
    }

    @Test
    void deveChamarMetodoExcluirDoRepositorio() throws BusinessException {
        // Preparação
        CnpjVo cnpj = new CnpjVo("12345678901234");

        // Ação
        //excluirRestaurante.executar(cnpj);

        // Verificação
        verify(restauranteRepository).excluir(cnpj);
    }
}
