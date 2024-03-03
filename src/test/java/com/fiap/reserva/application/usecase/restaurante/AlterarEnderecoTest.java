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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlterarEnderecoTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private AlterarEndereco alterarEndereco;

    @BeforeEach
    void setUp() {
        // Os mocks são inicializados automaticamente com o uso de @Mock e @InjectMocks.
        // Portanto, não há necessidade de inicialização explícita dentro deste método.
    }

    @Test
    void AlterarEnderecoSucesso() throws BusinessException {
        // Preparação dos dados de teste
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo novoEndereco = new EnderecoVo("20231-000", "Rua Magé", "980", "apto 401", "Perdizes", "São Paulo", "SP");

        // Configuração do mock para não realizar nenhuma ação quando o método alterar for chamado.
        // Este passo é opcional para métodos void, pois o comportamento padrão de um mock é não fazer nada.
        doNothing().when(enderecoRepository).alterar(eq(cnpj), any(EnderecoVo.class));

        // Execução do método sob teste
        alterarEndereco.executar(cnpj, novoEndereco);

        // Verificação para assegurar que o método alterar foi chamado no repositório
        // com os parâmetros corretos. Isso confirma que a lógica de alteração do endereço
        // foi acionada conforme esperado.
        verify(enderecoRepository).alterar(cnpj, novoEndereco);
    }

    @Test
    void AlterarEnderecoFalhaQuandoEnderecoVoNulo() {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        // Execução do método sob teste com expectativa de falha devido ao enderecoVo nulo
        BusinessException exception = assertThrows(BusinessException.class, () -> alterarEndereco.executar(cnpj, null));

        // Verificação para assegurar que a mensagem da exceção é conforme esperado
        assertEquals("Endereco é obrigatorio", exception.getMessage());

        // Verificação adicional para garantir que o método alterar não foi chamado no repositório
        verifyNoInteractions(enderecoRepository);
    }

}
