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

class CadastrarEnderecoTest {

    @Mock
    private EnderecoRepository repository;

    @InjectMocks
    private CadastrarEndereco cadastrarEndereco;

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
    void naoDeveCadastrarEnderecoQuandoEnderecoEhNulo() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        final Throwable throwable = assertThrows(BusinessException.class, () -> cadastrarEndereco.executar(cnpj, null));

        assertEquals("Endereco é obrigatorio", throwable.getMessage());
        verifyNoInteractions(repository);
    }
    @Test
    void deveCadastrarEnderecoComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo enderecoVo = new EnderecoVo("05020-000", "Rua Exemplo", "123", "Apto 1", "Bairro", "Cidade", "Estado");

        cadastrarEndereco.executar(cnpj, enderecoVo);

        verify(repository).cadastrar(cnpj, enderecoVo);
        verifyNoMoreInteractions(repository);
    }
}
