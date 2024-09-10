package com.example.tukgraduation.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CompileConfig {

    @Value("${rapidapi.key}")
    private String key;

    public static final String COMPILE_API_URL = "https://code-compiler10.p.rapidapi.com/";
    public static final String COMPILE_API_HOST = "code-compiler10.p.rapidapi.com";

}

