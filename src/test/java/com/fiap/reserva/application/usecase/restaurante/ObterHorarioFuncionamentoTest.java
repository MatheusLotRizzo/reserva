package com.fiap.reserva.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

class ObterHorarioFuncionamentoTest {

    @Mock
    private HorarioFuncionamentoRepository repository;

    @InjectMocks
    private ObterHorarioFuncionamento obterHorarioFuncionamento;

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
    void deveLancarExcecaoQuandoHorarioFuncionamentoNaoEncontrado() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(DayOfWeek.MONDAY, LocalDateTime.now(), LocalDateTime.now().plusHours(8));

        when(repository.obter(cnpj, horarioFuncionamento)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> obterHorarioFuncionamento.getObter(cnpj, horarioFuncionamento));

        assertEquals("Horário de funcionamento não encontrado", throwable.getMessage());
        verify(repository).obter(cnpj, horarioFuncionamento);
    }

    @Test
    void deveObterHorarioFuncionamento() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(DayOfWeek.MONDAY, LocalDateTime.now(), LocalDateTime.now().plusHours(8));

        when(repository.obter(cnpj, horarioFuncionamento)).thenReturn(horarioFuncionamento);

        HorarioFuncionamento resultado = obterHorarioFuncionamento.getObter(cnpj, horarioFuncionamento);

        assertNotNull(resultado);
        assertEquals(horarioFuncionamento, resultado);
        verify(repository).obter(cnpj, horarioFuncionamento);
    }
}
