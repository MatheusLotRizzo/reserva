package com.fiap.reserva.application.usecase.reserva;

import java.time.LocalDateTime;

import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;

public class ExcluirReservaRestaurante {

    private final ReservaRepository repository;

    public ExcluirReservaRestaurante(ReservaRepository repository) {
        this.repository = repository;
    }

    public void execultar(final String email, final String cnpj, final String dataHora){
        final EmailVo  emailVo = new EmailVo(email);
        final CnpjVo cnpjVo = new CnpjVo(cnpj);
        final LocalDateTime dataHoraTime = LocalDateTime.parse(dataHora);

        repository.excluir(emailVo, cnpjVo, dataHoraTime);
    }
}
