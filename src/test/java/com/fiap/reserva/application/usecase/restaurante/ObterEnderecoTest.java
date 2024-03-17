package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ObterEnderecoTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private ObterEndereco obterEndereco;

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
    void deveLancarExcecaoQuandoEnderecoNaoEncontrado() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo enderecoVo = new EnderecoVo("00000-000", "Rua Inexistente", "0", "", "Bairro", "Cidade", "Estado");

        when(enderecoRepository.obter(cnpj, enderecoVo)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> obterEndereco.getObter(cnpj, enderecoVo));

        assertEquals("Endereço não encontrado", throwable.getMessage());
        verify(enderecoRepository).obter(cnpj, enderecoVo);
    }

    @Test
    void deveObterEnderecoComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo enderecoEsperado = new EnderecoVo("05020-000", "Rua Exemplo", "123", "Apto 1", "Bairro", "Cidade", "Estado");

        when(enderecoRepository.obter(cnpj, enderecoEsperado)).thenReturn(enderecoEsperado);

        EnderecoVo resultado = obterEndereco.getObter(cnpj, enderecoEsperado);

        assertNotNull(resultado);
        assertEquals(enderecoEsperado, resultado);
        verify(enderecoRepository).obter(cnpj, enderecoEsperado);
    }
}
