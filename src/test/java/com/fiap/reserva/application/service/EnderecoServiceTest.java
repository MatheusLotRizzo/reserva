package com.fiap.reserva.application.service;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private CnpjVo cnpj;
    private EnderecoVo enderecoVo;

    @BeforeEach
    void setUp() throws BusinessException {
        MockitoAnnotations.openMocks(this);
        enderecoService = new EnderecoService(enderecoRepository);
        cnpj = new CnpjVo("12345678901234");
        enderecoVo = new EnderecoVo("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado");
    }

    @Test
    void deveCadastrarEnderecoComSucesso() throws BusinessException {
        enderecoService.cadastrar(cnpj, enderecoVo);
        verify(enderecoRepository).cadastrar(cnpj, enderecoVo);
    }

    @Test
    void deveAlterarEnderecoComSucesso() throws BusinessException {
        enderecoService.alterar(cnpj, enderecoVo);
        verify(enderecoRepository).alterar(cnpj, enderecoVo);
    }

    @Test
    void deveObterEnderecoComSucesso() throws BusinessException {
        when(enderecoRepository.obter(cnpj, enderecoVo)).thenReturn(enderecoVo);
        EnderecoVo obtido = enderecoService.getObter(cnpj, enderecoVo);
        verify(enderecoRepository).obter(cnpj, enderecoVo);
        assertEquals(enderecoVo, obtido);
    }
}
