package com.example.tukgraduation.chatroom.service;

import com.example.tukgraduation.chatroom.dto.CompilerRequest;
import com.example.tukgraduation.chatroom.dto.CompilerResponse;
import com.example.tukgraduation.global.config.CompileConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import static com.example.tukgraduation.global.config.CompileConfig.COMPILE_API_URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    private final CompileConfig compileConfig;
    public CompilerResponse getCompileResponse(CompilerRequest request) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(COMPILE_API_URL);

        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        return webclient.post()
                .header("X-RapidAPI-Key", compileConfig.getKey())
                .header("X-RapidAPI-Host", CompileConfig.COMPILE_API_HOST)
                .header("content-type", "application/json")
                .header("x-compile", "rapidapi")
                .bodyValue(request)// await
                .exchangeToMono(response -> {
                    int httpStatusCode = response.statusCode().value();
                    HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode);
                    if (httpStatus.is2xxSuccessful()) {
                        return response.bodyToMono(CompilerResponse.class)
                                .switchIfEmpty(Mono.error(new RuntimeException("Empty response body")));
                    } else {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Error body: {}", errorBody);
                                    return Mono.error(new RuntimeException("Failed with status: " + httpStatusCode + ", body: " + errorBody));
                                });
                    }
                }).block();
    }

}