package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public interface EnderecoRepository {
    void cadastrar(CnpjVo cnpj, EnderecoVo enderecoVo);
    void alterar(CnpjVo cnpj, EnderecoVo enderecoVo);

}
