package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

public interface EnderecoRepository {
    void cadastrar(CnpjVo cnpj, EnderecoVo enderecoVo);
    void alterar(CnpjVo cnpj, EnderecoVo enderecoVo);
    EnderecoVo obter(CnpjVo cnpj, EnderecoVo enderecoVo);

}
