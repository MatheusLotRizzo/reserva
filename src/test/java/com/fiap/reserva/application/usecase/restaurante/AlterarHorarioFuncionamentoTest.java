package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlterarHorarioFuncionamentoTest {

    @Mock
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @InjectMocks
    private AlterarHorarioFuncionamento alterarHorarioFuncionamento;

    private CnpjVo cnpjValido;
    private HorarioFuncionamento horarioFuncionamentoValido;

    @BeforeEach
    void setUp() {
        cnpjValido = new CnpjVo("12345678901234");
        // Exemplo de horários inicial e final para testar
        LocalDateTime horarioInicial = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime horarioFinal = LocalDateTime.of(2023, 10, 1, 18, 0);
        horarioFuncionamentoValido = new HorarioFuncionamento(DayOfWeek.MONDAY,horarioInicial, horarioFinal);
    }

    @Test
    void deveAlterarHorarioFuncionamentoComSucesso() throws BusinessException {
        // Ação
        alterarHorarioFuncionamento.executar(cnpjValido, horarioFuncionamentoValido);

        // Verificação
        verify(horarioFuncionamentoRepository).alterar(eq(cnpjValido), eq(horarioFuncionamentoValido));
    }

    @Test
    void deveLancarExceptionQuandoHorarioFuncionamentoForNulo() {
        // Expectativa
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            // Ação
            alterarHorarioFuncionamento.executar(cnpjValido, null);
        });

        // Verificação
        assertEquals("Horario Funcionamento é obrigatorio", thrown.getMessage());
        verifyNoInteractions(horarioFuncionamentoRepository);
    }
}
