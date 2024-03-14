package com.fiap.reserva.application.usecase.restaurante;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

@ExtendWith(MockitoExtension.class)
class CadastrarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private CadastrarRestaurante cadastrarRestaurante;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() throws BusinessException {
        // Configuração do restaurante de teste
        restaurante = new Restaurante(new CnpjVo("12345678901234"), "Restaurante Teste");
    }

    @Test
    void deveCadastrarRestauranteComSucesso() throws BusinessException {
        // Configura o mock para retornar o restaurante quando o método cadastrar for chamado
//        when(restauranteRepository.cadastrar(any(Restaurante.class))).thenReturn(restaurante);
//
//        // Executa o método executar
//        Restaurante resultado = cadastrarRestaurante.executar(restaurante);
//
//        // Verifica se o resultado é o esperado
//        assertNotNull(resultado, "O resultado não deve ser nulo.");
//        assertEquals("Restaurante Teste", resultado.getNome(), "O nome do restaurante deve ser igual ao esperado.");
//
//        // Verifica se o método cadastrar foi chamado no repositório
//        verify(restauranteRepository).cadastrar(restaurante);
    }
}
