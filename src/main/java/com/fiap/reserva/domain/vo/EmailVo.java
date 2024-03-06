package com.fiap.reserva.domain.vo;

import java.util.Objects;

public class EmailVo {
    private final String regex = "^[\\w.]+@[\\w.]+\\.[a-zA-Z]+(\\.[a-zA-Z])?+$";
    private final String endereco;

    public EmailVo(String endereco) {
        if(endereco == null || !endereco.matches(regex) || endereco.split("@")[0].matches("^[^a-zA-Z0-9]*$")){
            throw new IllegalArgumentException("E-mail inválido");
        }
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        EmailVo emailVo = (EmailVo) obj;
        return Objects.equals(endereco, emailVo.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endereco);
    }
}
