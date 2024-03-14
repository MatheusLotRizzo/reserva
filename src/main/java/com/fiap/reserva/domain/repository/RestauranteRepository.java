package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public interface RestauranteRepository {
    Restaurante buscarPorCnpj(CnpjVo cnpj) throws BusinessException;
    Restaurante buscarPorNome(String nome) throws BusinessException;
    List<Restaurante> buscarPorTipoCozinha(TipoCozinha tipoCozinha) throws BusinessException;
    Integer obterLotacaoMaximaRestaurante(Restaurante restaurante) throws BusinessException;
    List<Restaurante> buscarPorLocalizacao(EnderecoVo enderecoVo) throws BusinessException;
    Restaurante cadastrar(Restaurante restaurante) throws BusinessException;
    Restaurante alterar(Restaurante restaurante) throws BusinessException;
    void excluir(CnpjVo cnpj) throws BusinessException;
}
