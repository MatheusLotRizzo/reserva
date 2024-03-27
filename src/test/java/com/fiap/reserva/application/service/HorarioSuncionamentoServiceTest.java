package com.fiap.reserva.application.service;

import static org.mockito.Mockito.verify;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

class HorarioSuncionamentoServiceTest {

    @Mock
    private HorarioFuncionamentoRepository repository;

    @InjectMocks
    private HorarioSuncionamentoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarHorarioFuncionamentoComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        HorarioFuncionamento horario = new HorarioFuncionamento(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 15, 9, 0), LocalDateTime.of(2023, 3, 15, 17, 0));

        service.cadastrar(cnpj, horario);

        verify(repository).cadastrar(cnpj, horario);
    }

    @Test
    void deveAlterarHorarioFuncionamentoComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        HorarioFuncionamento horario = new HorarioFuncionamento(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 15, 18, 0), LocalDateTime.of(2023, 3, 15, 23, 0));

        service.alterar(cnpj, horario);

        verify(repository).alterar(cnpj, horario);
    }
}
