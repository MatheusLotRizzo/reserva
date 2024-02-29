package com.fiap.reserva.application.usecase.restaurante;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

public class AlterarEndereco {
    private final EnderecoRepository repository;
    public AlterarEndereco(EnderecoRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public void executar(CnpjVo cnpj, EnderecoVo enderecoVo) throws BusinessException {
        if(enderecoVo == null){
            throw new BusinessException("Endereco é obrigatorio");
        }

        repository.alterar(cnpj,enderecoVo);
    }
}
