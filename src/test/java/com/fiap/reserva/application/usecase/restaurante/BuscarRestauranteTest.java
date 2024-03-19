package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BuscarRestauranteTest {

    @Mock
    private RestauranteRepository repository;

    @InjectMocks
    private BuscarRestaurante buscarRestaurante;

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
    void naoDeveRetornarRestauranteQuandoCnpjInvalido() throws BusinessException {
        final String cnpjInvalido = "123"; // CNPJ inválido
        final Throwable throwable = assertThrows(BusinessException.class, () -> new CnpjVo(cnpjInvalido));
        assertEquals("Número de CNPJ inválido", throwable.getMessage());
    }

    @Test
    void naoDeveRetornarRestauranteQuandoCnpjNaoEncontrado() throws BusinessException {
        CnpjVo cnpjNaoEncontrado = new CnpjVo("12345678901234");
        when(repository.buscarPorCnpj(cnpjNaoEncontrado)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> buscarRestaurante.getRestaurantePor(cnpjNaoEncontrado));

        assertEquals("Restaurante não encontrado", throwable.getMessage());
        verify(repository).buscarPorCnpj(cnpjNaoEncontrado);
    }

    @Test
    void deveRetornarRestaurantePorCnpj() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante esperado = new Restaurante(cnpj, "Restaurante Teste");
        when(repository.buscarPorCnpj(cnpj)).thenReturn(esperado);

        Restaurante resultado = buscarRestaurante.getRestaurantePor(cnpj);

        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        verify(repository).buscarPorCnpj(cnpj);
    }

    @Test
    void deveLancarExcecaoQuandoNomeNaoEncontrado() throws BusinessException {
        String nomeInexistente = "Nome Não Existe";
        when(repository.buscarPorNome(nomeInexistente)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> buscarRestaurante.getRestaurantePorNome(nomeInexistente));
        assertEquals("Restaurante não encontrado para o nome: " + nomeInexistente, throwable.getMessage());
        verify(repository).buscarPorNome(nomeInexistente);
    }

    @Test
    void deveRetornarRestaurantePorNome() throws BusinessException {
        String nome = "Restaurante Teste";
        Restaurante esperado = new Restaurante(new CnpjVo("12345678901234"), nome);
        when(repository.buscarPorNome(nome)).thenReturn(esperado);

        Restaurante resultado = buscarRestaurante.getRestaurantePorNome(nome);

        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        verify(repository).buscarPorNome(nome);
    }

    @Test
    void deveRetornarRestaurantesPorTipoCozinha() throws BusinessException {
        TipoCozinha tipoCozinha = TipoCozinha.ITALIANA;
        Restaurante restauranteEsperado = new Restaurante(new CnpjVo("12345678901234"), "Restaurante Italiano");
        List<Restaurante> esperados = List.of(restauranteEsperado);
        when(repository.buscarPorTipoCozinha(tipoCozinha)).thenReturn(esperados);

        List<Restaurante> resultados = buscarRestaurante.getRestaurantePorTipoCozinha(tipoCozinha);

        assertEquals(esperados, resultados);
        verify(repository).buscarPorTipoCozinha(tipoCozinha);
    }

    @Test
    void deveRetornarRestaurantesPorLocalizacao() throws BusinessException {
        EnderecoVo enderecoVo = new EnderecoVo("05020-000", "Rua Exemplo", "123", "Apto 1", "Bairro", "Cidade", "Estado");
        List<Restaurante> esperados = List.of(new Restaurante(new CnpjVo("12345678901234"), "Restaurante Local"));
        when(repository.buscarPorLocalizacao(enderecoVo)).thenReturn(esperados);

        List<Restaurante> resultados = buscarRestaurante.getRestaurantePorLocalizacao(enderecoVo);

        assertEquals(esperados, resultados);
        verify(repository).buscarPorLocalizacao(enderecoVo);
    }
}
