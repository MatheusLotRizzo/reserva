package com.fiap.spring.swagger.annotations.responses;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })

@ApiResponse(
	responseCode = "204", 
	description = "Operação realizada com sucesso, mas sem arquivo de retorno",
	content = @Content()
)
public @interface ApiResponseNoContentJson{}
