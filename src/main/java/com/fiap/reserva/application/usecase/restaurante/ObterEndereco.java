package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public class ObterEndereco {

    private final EnderecoRepository repository;
    public ObterEndereco(EnderecoRepository enderecoRepository) {
        this.repository =  enderecoRepository;
    }

    public EnderecoVo getObter(final CnpjVo cnpj, final EnderecoVo enderecoVo) throws BusinessException  {
        return repository.obter(cnpj,enderecoVo);
    }

}
