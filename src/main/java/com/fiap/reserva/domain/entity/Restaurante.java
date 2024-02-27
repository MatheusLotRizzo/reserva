package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public class Restaurante {
    private CnpjVo cnpj;
    private String nome;
    private EnderecoVo endereco;
    private List<HorarioFuncionamento> horarioFuncionamento ;
    private int capacidade;
    private TipoCozinha tipoCozinha;

    public Restaurante(CnpjVo cnpj, String nome, EnderecoVo endereco, List<HorarioFuncionamento> horarioFuncionamento, int capacidade, TipoCozinha tipoCozinha) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidade = capacidade;
        this.tipoCozinha = tipoCozinha;
    }

    public Restaurante(String cnpj) {
        this.cnpj = new CnpjVo(cnpj);
    }

    public CnpjVo getCnpj() {
        return cnpj;
    }

    public String getCnpjString() {
        return cnpj.getNumero();
    }
}
