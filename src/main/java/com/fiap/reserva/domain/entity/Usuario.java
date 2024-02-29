package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;
import com.fiap.spring.Controller.Dto.UsuarioDto;

public class Usuario {
    private final String nome;
    private final EmailVo email;
    private final String celular;

    public Usuario(String nome, EmailVo email, String celular) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
    }

    public Usuario(String nome, String email, String celular){
        this.nome = nome;
        this.email = new EmailVo(email);
        this.celular = celular;
    }

    public Usuario(String email){
        this.nome = null;
        this.email = new EmailVo(email);
        this.celular = null;
    }

    public EmailVo getEmail() {
        return email;
    }

    public String getEmailString() {
        return email.getEndereco();
    }

    public String getNome() {
        return nome;
    }

    public String getCelular() {
        return celular;
    }

    public UsuarioDto toDto(){
        return new UsuarioDto(
                this.nome,
                this.getEmailString(),
                this.celular
        );
    }

}
