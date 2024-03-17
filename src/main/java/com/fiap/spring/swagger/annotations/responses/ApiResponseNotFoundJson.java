package com.fiap.spring.swagger.annotations.responses;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import com.fiap.spring.Controller.MessageErrorHandler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })

@ApiResponse(
	responseCode = "404", 
	description = "Registro solicitado não encontrado.",
    content = { 
		@Content(
			mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = MessageErrorHandler.class)
		)
})
public @interface ApiResponseNotFoundJson {}
