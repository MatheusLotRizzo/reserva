package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.spring.Controller.Dto.EnderecoDto;
import com.fiap.spring.Controller.Dto.HorarioFuncionamentoDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;
import com.fiap.spring.Controller.Dto.UsuarioDto;

import java.util.List;
import java.util.stream.Collectors;

public class Restaurante {
    private CnpjVo cnpj;
    private String nome;
    private EnderecoVo endereco;
    private HorarioFuncionamento horarioFuncionamento ;
    private int capacidade;
    private TipoCozinha tipoCozinha;

    public Restaurante(CnpjVo cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    public Restaurante(String cnpj, String nome) {
        this.cnpj = new CnpjVo(cnpj);
        this.nome = nome;
    }

    public Restaurante(CnpjVo cnpj, String nome, EnderecoVo endereco, HorarioFuncionamento horarioFuncionamento, int capacidade, TipoCozinha tipoCozinha) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidade = capacidade;
        this.tipoCozinha = tipoCozinha;
    }

    public Restaurante(String cnpj, String nome, EnderecoVo endereco, HorarioFuncionamento horarioFuncionamento, int capacidade, TipoCozinha tipoCozinha) {
        this(cnpj);
        this.nome = nome;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidade = capacidade;
        this.tipoCozinha = tipoCozinha;
    }

    public Restaurante(String cnpj) {
        this.cnpj = new CnpjVo(cnpj);
    }

    public String getCnpjString() {
        return cnpj.getNumero();
    }

    public CnpjVo getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public TipoCozinha getTipoCozinha() {
        return tipoCozinha;
    }

    public EnderecoVo getEndereco() {
        return endereco;
    }

    public HorarioFuncionamento getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public RestauranteDto toDto(){
        final HorarioFuncionamentoDto horarioFuncionamentoDtos = this.horarioFuncionamento.toDto();
        final EnderecoDto enderecoDto = this.endereco.toDto();

        return new RestauranteDto(
                this.getCnpjString(),
                this.nome,
                this.capacidade,
                this.tipoCozinha.toString(),
                horarioFuncionamentoDtos,
                enderecoDto
        );
    }

}
