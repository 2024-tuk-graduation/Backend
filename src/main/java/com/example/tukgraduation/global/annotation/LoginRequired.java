package com.example.tukgraduation.global.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//로그인이 필요한지 체크
public @interface LoginRequired {}

