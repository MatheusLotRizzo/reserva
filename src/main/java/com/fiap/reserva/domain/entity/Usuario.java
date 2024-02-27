package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.vo.EmailVo;

public class Usuario {
    private final String nome;
    private final EmailVo email;

    public Usuario(String nome, EmailVo email) {
        this.nome = nome;
        this.email = email;
    }

    public Usuario(String nome, String email){
        this.nome = nome;
        this.email = new EmailVo(email);
    }

    public Usuario(String email){
        this.nome = null;
        this.email = new EmailVo(email);
    }

    public EmailVo getEmail() {
        return email;
    }

    public String getEmailString() {
        return email.getEndereco();
    }
}
