package com.fiap.reserva.domain.vo;

import com.fiap.reserva.domain.exception.BusinessException;

public class CnpjVo {
    private final String numero;
    public CnpjVo(String numero) throws BusinessException {
        if (numero == null || !isValidCnpj(numero)) {
            throw new BusinessException("Número de CNPJ inválido");
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
