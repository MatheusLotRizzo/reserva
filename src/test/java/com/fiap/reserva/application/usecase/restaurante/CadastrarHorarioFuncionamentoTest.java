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

class CadastrarHorarioFuncionamentoTest {

    @Mock
    private HorarioFuncionamentoRepository repository;

    @InjectMocks
    private CadastrarHorarioFuncionamento cadastrarHorarioFuncionamento;

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
    void naoDeveCadastrarHorarioFuncionamentoQuandoNulo() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        final Throwable throwable = assertThrows(BusinessException.class,
                () -> cadastrarHorarioFuncionamento.executar(cnpj, null));

        assertEquals("Horario Funcionamento é obrigatorio", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveCadastrarHorarioFuncionamentoComSucesso() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        LocalDateTime inicio = LocalDateTime.of(2024, 3, 1, 9, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 3, 1, 18, 0);
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(DayOfWeek.MONDAY, inicio, fim);

        cadastrarHorarioFuncionamento.executar(cnpj, horarioFuncionamento);

        verify(repository).cadastrar(cnpj, horarioFuncionamento);
        verifyNoMoreInteractions(repository);
    }
}