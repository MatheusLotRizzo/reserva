package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.vo.EmailVo;

public class Usuario {
    private String nome;
    private EmailVo email;

    public Usuario(String nome, EmailVo email) {
        this.nome = nome;
        this.email = email;
    }

    public EmailVo getEmail() {
        return email;
    }

    public String getEmailString() {
        return email.getEndereco();
    }
}
