package com.fiap.reserva.application.usecase.restaurante;

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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private BuscarRestaurante buscarRestaurante;

    @BeforeEach
    void setUp() {
    }

    @Test
    void deveRetornarRestaurantePorCnpj() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante esperado = new Restaurante(cnpj, "Restaurante Teste");
        when(restauranteRepository.buscarPorCnpj(cnpj)).thenReturn(esperado);

        Restaurante resultado = buscarRestaurante.getRestaurantePor(cnpj);
        assertEquals(esperado, resultado);
    }

    @Test
    void deveRetornarRestaurantePorNome() throws BusinessException {
        String nome = "Restaurante Teste";
        Restaurante esperado = new Restaurante(new CnpjVo("12345678901234"), nome);
        when(restauranteRepository.buscarPorNome(nome)).thenReturn(esperado);

        Restaurante resultado = buscarRestaurante.getRestaurantePorNome(nome);
        assertEquals(esperado, resultado);
    }

    @Test
    void deveRetornarRestaurantesPorTipoCozinha() {
        TipoCozinha tipoCozinha = TipoCozinha.ITALIANA;
        List<Restaurante> esperados = Arrays.asList(new Restaurante(new CnpjVo("12345678901234"), "Restaurante Italiano"));
        when(restauranteRepository.buscarPorTipoCozinha(tipoCozinha)).thenReturn(esperados);

        List<Restaurante> resultados = buscarRestaurante.getRestaurantePorTipoCozinha(tipoCozinha);
        assertEquals(esperados, resultados);
    }

    @Test
    void deveRetornarRestaurantesPorLocalizacao() {
        EnderecoVo enderecoVo = new EnderecoVo("05020-000", "Rua Exemplo", "123", "Apto 1", "Bairro", "Cidade", "Estado");
        List<Restaurante> esperados = Arrays.asList(new Restaurante(new CnpjVo("12345678901234"), "Restaurante Exemplo"));
        when(restauranteRepository.buscarPorLocalizacao(enderecoVo)).thenReturn(esperados);

        List<Restaurante> resultados = buscarRestaurante.getRestaurantePorLocalizacao(enderecoVo);
        assertEquals(esperados, resultados);
    }
}
