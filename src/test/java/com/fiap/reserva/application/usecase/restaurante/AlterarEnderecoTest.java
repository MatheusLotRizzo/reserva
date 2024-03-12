package com.fiap.reserva.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

class AlterarEnderecoTest {

    @Mock
    private EnderecoRepository repository;

    @InjectMocks
    private AlterarEndereco alterarEndereco;

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
    void deveLancarExcecaoQuandoEnderecoEhNulo() {
        final Throwable throwable = assertThrows(
                BusinessException.class,
                () -> alterarEndereco.executar(new CnpjVo("12345678901234"), null)
        );

        assertEquals("Endereco é obrigatorio", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveAlterarEnderecoComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo enderecoVo = new EnderecoVo("05020-000", "Rua Exemplo", "123", "Apto 1", "Bairro", "Cidade", "Estado");

        alterarEndereco.executar(cnpj, enderecoVo);

        verify(repository).alterar(cnpj, enderecoVo);
        verifyNoMoreInteractions(repository);
    }
}
