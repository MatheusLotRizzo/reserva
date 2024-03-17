package com.fiap.spring.swagger.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.fiap.spring.swagger.annotations.responses.ApiResponseBadRequestJson;
import com.fiap.spring.swagger.annotations.responses.ApiResponseCreateJson;
import com.fiap.spring.swagger.annotations.responses.ApiResponseNotFoundJson;

@Retention(RUNTIME)
@Target({ METHOD })
@ApiResponseCreateJson
@ApiResponseNotFoundJson
@ApiResponseBadRequestJson
public @interface ApiResponseSwaggerCreate {}
