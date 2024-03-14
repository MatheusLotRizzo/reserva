package com.fiap.reserva.domain.entity;

import java.util.List;
import java.util.Objects;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

public class Restaurante {
    private final CnpjVo cnpj;
    private final String nome;
    private final EnderecoVo endereco;
    private final List<HorarioFuncionamento> horarioFuncionamento ;
    private final int capacidadeMesas;
    private final TipoCozinha tipoCozinha;

    public Restaurante(CnpjVo cnpj, String nome) {
    	 this.cnpj = cnpj;
         this.nome = nome;
         this.endereco = null;
         this.horarioFuncionamento = null;
         this.capacidadeMesas = 0;
         this.tipoCozinha = null;
    }

    public Restaurante(CnpjVo cnpj, String nome, EnderecoVo endereco, List<HorarioFuncionamento> horarioFuncionamento, int capacidadeMesas, TipoCozinha tipoCozinha) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidadeMesas = capacidadeMesas;
        this.tipoCozinha = tipoCozinha;
    }

    public Restaurante(String cnpj, String nome, EnderecoVo endereco, List<HorarioFuncionamento> horarioFuncionamento, int capacidadeMesas, TipoCozinha tipoCozinha) throws BusinessException  {
        this(new CnpjVo(cnpj), nome, endereco, horarioFuncionamento, capacidadeMesas, tipoCozinha);
    }

    public Restaurante(String cnpj) throws BusinessException {
        this(new CnpjVo(cnpj), null);
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

    public List<HorarioFuncionamento> getHorarioFuncionamento() {
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



}
