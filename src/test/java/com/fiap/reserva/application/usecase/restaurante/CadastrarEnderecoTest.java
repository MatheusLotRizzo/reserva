package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CadastrarEnderecoTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private CadastrarEndereco cadastrarEndereco;

    @BeforeEach
    void setUp() {
    }

    @Test
    void executarSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo enderecoVo = new EnderecoVo("20231-000", "Rua Exemplo", "123", "Apto 1", "Perdizes", "São Paulo", "SP");

        doNothing().when(enderecoRepository).cadastrar(any(CnpjVo.class), any(EnderecoVo.class));

        assertDoesNotThrow(() -> cadastrarEndereco.executar(cnpj, enderecoVo));
        verify(enderecoRepository).cadastrar(cnpj, enderecoVo);
    }

    @Test
    void executarFalhaEnderecoNulo() {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        BusinessException exception = assertThrows(BusinessException.class, () -> cadastrarEndereco.executar(cnpj, null));
        assertEquals("Endereco é obrigatorio", exception.getMessage());
    }
}
