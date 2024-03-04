package com.fiap.reserva.domain.vo;

import com.fiap.spring.Controller.Dto.EnderecoDto;

public class EnderecoVo {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    public EnderecoVo(String cep, String logradouro, String numero, String complemento, String bairro, String cidade, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public EnderecoDto toDto(){
        return new EnderecoDto(
                this.cep,
                this.logradouro,
                this.numero,
                this.complemento,
                this.bairro,
                this.cidade,
                this.estado
        );
    }
}
