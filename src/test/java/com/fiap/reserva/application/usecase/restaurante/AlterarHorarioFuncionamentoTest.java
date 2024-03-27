package com.fiap.reserva.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

class AlterarHorarioFuncionamentoTest {

    @Mock
    private HorarioFuncionamentoRepository repository;

    @InjectMocks
    private AlterarHorarioFuncionamento alterarHorarioFuncionamento;
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
    void deveLancarExcecaoQuandoHorarioFuncionamentoEhNulo() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        final Throwable throwable = assertThrows(
                BusinessException.class,
                () -> alterarHorarioFuncionamento.executar(cnpj, null)
        );

        assertEquals("Horario Funcionamento é obrigatorio", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveAlterarHorarioFuncionamentoComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 15, 9, 0), LocalDateTime.of(2023, 3, 15, 17, 0));

        doNothing().when(repository).alterar(cnpj, horarioFuncionamento);

        alterarHorarioFuncionamento.executar(cnpj, horarioFuncionamento);

        verify(repository).alterar(cnpj, horarioFuncionamento);
        verifyNoMoreInteractions(repository);
    }
}
