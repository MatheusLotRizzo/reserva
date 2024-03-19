package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.spring.Controller.Dto.UsuarioDto;

import java.util.Objects;

public class Usuario {
    private final String nome;
    private final EmailVo email;
    private final String celular;
    
    public Usuario(String nome, EmailVo email, String celular) throws BusinessException {
        if (nome == null || nome.isEmpty()){
            throw new BusinessException("O nome não pode ser vazio");
        }
        this.nome = nome;
        this.email = email;
        this.celular = celular;
    }
    
    public Usuario(String nome, String email) throws BusinessException{
    	this(nome, new EmailVo(email), null);
    }

    public Usuario(String nome, String email, String celular) throws BusinessException{
        this(nome, new EmailVo(email), celular);
    }

    public Usuario(String email) throws BusinessException{
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

    @Override
	public int hashCode() {
		return Objects.hash(email);
	}

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Usuario){
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }

	public UsuarioDto toDto(){
        return new UsuarioDto(
                this.nome,
                this.getEmailString(),
                this.celular
        );
    }
    
}
