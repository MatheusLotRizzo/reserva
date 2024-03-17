package com.fiap.spring.swagger.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.fiap.spring.swagger.annotations.responses.ApiResponseBadRequestJson;
import com.fiap.spring.swagger.annotations.responses.ApiResponseNotFoundJson;
import com.fiap.spring.swagger.annotations.responses.ApiResponseOkJson;

@Retention(RUNTIME)
@Target({ METHOD })
@ApiResponseOkJson
@ApiResponseNotFoundJson
@ApiResponseBadRequestJson
public @interface ApiResponseSwaggerOk {}
