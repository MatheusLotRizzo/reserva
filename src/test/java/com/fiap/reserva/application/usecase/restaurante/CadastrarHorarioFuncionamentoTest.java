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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CadastrarHorarioFuncionamentoTest {

    @Mock
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @InjectMocks
    private CadastrarHorarioFuncionamento cadastrarHorarioFuncionamento;

    @BeforeEach
    void setUp() {
    }

    @Test
    void executarSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(DayOfWeek.MONDAY, LocalDateTime.now(), LocalDateTime.now().plusHours(8));

        doNothing().when(horarioFuncionamentoRepository).cadastrar(any(CnpjVo.class), any(HorarioFuncionamento.class));

        assertDoesNotThrow(() -> cadastrarHorarioFuncionamento.executar(cnpj, horarioFuncionamento));
        verify(horarioFuncionamentoRepository).cadastrar(cnpj, horarioFuncionamento);
    }

    @Test
    void executarFalhaHorarioFuncionamentoNulo() {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        BusinessException exception = assertThrows(BusinessException.class, () -> cadastrarHorarioFuncionamento.executar(cnpj, null));
        assertEquals("Horario Funcionamento é obrigatorio", exception.getMessage());
    }
}
