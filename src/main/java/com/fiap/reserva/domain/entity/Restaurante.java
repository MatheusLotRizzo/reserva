package com.fiap.reserva.domain.entity;

import java.util.Objects;

import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.spring.Controller.Dto.EnderecoDto;
import com.fiap.spring.Controller.Dto.HorarioFuncionamentoDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;

public class Restaurante {
    private CnpjVo cnpj;
    private String nome;
    private EnderecoVo endereco;
    private HorarioFuncionamento horarioFuncionamento ;
    private int capacidadeMesas;
    private TipoCozinha tipoCozinha;

    public Restaurante(CnpjVo cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    public Restaurante(String cnpj, String nome) {
        this.cnpj = new CnpjVo(cnpj);
        this.nome = nome;
    }

    public Restaurante(CnpjVo cnpj, String nome, EnderecoVo endereco, HorarioFuncionamento horarioFuncionamento, int capacidadeMesas, TipoCozinha tipoCozinha) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidadeMesas = capacidadeMesas;
        this.tipoCozinha = tipoCozinha;
    }

    public Restaurante(String cnpj, String nome, EnderecoVo endereco, HorarioFuncionamento horarioFuncionamento, int capacidadeMesas, TipoCozinha tipoCozinha) {
        this(cnpj);
        this.nome = nome;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidadeMesas = capacidadeMesas;
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

    public int getCapacidadeMesas() {
        return capacidadeMesas;
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
    
    @Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurante other = (Restaurante) obj;
		return Objects.equals(cnpj, other.cnpj);
	}

	public RestauranteDto toDto(){
        final HorarioFuncionamentoDto horarioFuncionamentoDtos = this.horarioFuncionamento.toDto();
        final EnderecoDto enderecoDto = this.endereco.toDto();

        return new RestauranteDto(
                this.getCnpjString(),
                this.nome,
                this.capacidadeMesas,
                this.tipoCozinha,
                horarioFuncionamentoDtos,
                enderecoDto
        );
    }

}
