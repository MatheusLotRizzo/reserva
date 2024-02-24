package com.fiap.reserva.domain.repository;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.vo.CnpjVo;

import java.util.List;

public interface RestauranteRepository {
    Restaurante buscarPorCnpj(CnpjVo cnpj);
    Restaurante buscarPorNome(String nome);
    Restaurante buscarPorTipoCozinha(TipoCozinha tipoCozinha);
    void cadastrar(Restaurante restaurante);
    void alterar(Restaurante restaurante);
    void excluir(Restaurante restaurante);
}
