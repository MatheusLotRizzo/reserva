package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public interface RestauranteRepository {
    Restaurante buscarPorCnpj(CnpjVo cnpj);
    Restaurante buscarPorNome(String nome);
    List<Restaurante> buscarPorTipoCozinha(TipoCozinha tipoCozinha);
    Integer obterLotacaoMaximaRestaurante(Restaurante restaurante);
    List<Restaurante> buscarPorLocalizacao(EnderecoVo enderecoVo);
    Restaurante cadastrar(Restaurante restaurante);
    Restaurante alterar(Restaurante restaurante);
    void excluir(CnpjVo cnpj);
}
