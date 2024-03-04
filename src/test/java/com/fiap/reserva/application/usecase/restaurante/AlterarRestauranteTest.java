package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlterarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private AlterarRestaurante alterarRestaurante;

    private Restaurante restauranteValido;

    @BeforeEach
    void setUp() {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo endereco = new EnderecoVo("05020-000", "Rua Exemplo", "123", "Apto 1", "Bairro", "Cidade", "Estado");
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(LocalDateTime.now(), LocalDateTime.now().plusHours(8));
        restauranteValido = new Restaurante(cnpj, "Restaurante Teste", endereco, horarioFuncionamento, 100, TipoCozinha.ITALIANA);

        // Marque esta simulação como leniente se for necessária apenas em alguns testes
        lenient().when(restauranteRepository.buscarPorCnpj(cnpj)).thenReturn(restauranteValido);
    }



    @Test
    void deveAlterarRestauranteComSucesso() throws BusinessException {
        when(restauranteRepository.alterar(any(Restaurante.class))).thenReturn(restauranteValido);

        Restaurante resultado = alterarRestaurante.executar(restauranteValido);

        assertEquals(restauranteValido, resultado);
        verify(restauranteRepository).alterar(restauranteValido);
    }

    @Test
    void deveLancarExceptionQuandoRestauranteForNulo() {
        assertThrows(BusinessException.class, () -> alterarRestaurante.executar(null));
    }

    @Test
    void deveLancarExceptionQuandoRestauranteNaoForEncontrado() {
        CnpjVo cnpjNaoEncontrado = new CnpjVo("99999999999999");
        Restaurante restauranteNaoEncontrado = new Restaurante(cnpjNaoEncontrado, "Restaurante Fantasma");

        // Ajuste para usar buscarPorCnpj e simular que não encontrou o restaurante (retorna null)
        when(restauranteRepository.buscarPorCnpj(cnpjNaoEncontrado)).thenReturn(null);

        BusinessException thrown = assertThrows(BusinessException.class, () -> alterarRestaurante.executar(restauranteNaoEncontrado));

        assertEquals("Restaurante não pode ser alterado, pois nao foi encontrada", thrown.getMessage());
    }
}
