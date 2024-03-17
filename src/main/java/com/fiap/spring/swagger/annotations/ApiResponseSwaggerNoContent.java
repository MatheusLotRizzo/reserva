package com.fiap.spring.swagger.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.fiap.spring.swagger.annotations.responses.ApiResponseBadRequestJson;
import com.fiap.spring.swagger.annotations.responses.ApiResponseNoContentJson;
import com.fiap.spring.swagger.annotations.responses.ApiResponseNotFoundJson;

@Retention(RUNTIME)
@Target({ METHOD })
@ApiResponseNoContentJson
@ApiResponseNotFoundJson
@ApiResponseBadRequestJson
public @interface ApiResponseSwaggerNoContent {}
