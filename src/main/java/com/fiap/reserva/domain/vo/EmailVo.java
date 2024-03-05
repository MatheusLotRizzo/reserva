package com.fiap.reserva.domain.vo;

public class EmailVo {
    private final String regex = "^[\\w.]+@[\\w.]+\\.[a-zA-Z]+(\\.[a-zA-Z])?+$";
    private final String endereco;

    public EmailVo(String endereco) {
        if(endereco == null || !endereco.matches(regex)){
            throw new IllegalArgumentException("E-mail inválido");
        }

        if (endereco.split("@")[0].matches("^[^a-zA-Z0-9]*$")){
            throw new IllegalArgumentException("O e-mail deve conter letras");
        }
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }
}
