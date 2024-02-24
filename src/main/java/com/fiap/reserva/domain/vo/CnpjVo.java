package com.fiap.reserva.domain.vo;

public class CnpjVo {
    private final String numero;
    public CnpjVo(String numero) {
        if (numero == null || !isValidCnpj(numero)) {
            throw new IllegalArgumentException("Número de CNPJ inválido");
        }
        this.numero = numero;
    }

    private boolean isValidCnpj(String numero) {
        return numero != null && numero.length() == 14;
    }

    public String getNumero() {
        return numero;
    }
}
