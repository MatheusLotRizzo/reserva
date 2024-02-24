package com.fiap.reserva.domain.vo;

public class EmailVo {
    private final String regex = "^[\\w.]+@[\\w.]+\\.[\\a-zA-Z]+(\\.[\\a-zA-Z])?+$";
    private final String endereco;

    public EmailVo(String endereco) {
        if(endereco == null || !endereco.matches(regex)){
            throw new IllegalArgumentException("E-mail invalido");
        }
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }
}
