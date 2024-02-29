package com.fiap.reserva.domain.vo;

import com.fiap.spring.Controller.Dto.EnderecoDto;
import com.fiap.spring.Controller.Dto.HorarioFuncionamentoDto;

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
