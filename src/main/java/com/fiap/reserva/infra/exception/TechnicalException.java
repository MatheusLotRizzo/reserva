package com.fiap.reserva.infra.exception;

public class TechnicalException extends RuntimeException {
    
    public TechnicalException(Throwable throwable){
        super(throwable);
        throwable.printStackTrace();
    }

}
