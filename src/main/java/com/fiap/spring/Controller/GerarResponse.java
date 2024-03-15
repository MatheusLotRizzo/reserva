package com.fiap.spring.Controller;

import com.fiap.reserva.domain.exception.BusinessException;

@FunctionalInterface
public interface GerarResponse<T> {

	T get() throws  BusinessException;
}
