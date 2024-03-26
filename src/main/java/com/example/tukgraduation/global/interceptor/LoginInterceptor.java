package com.example.tukgraduation.global.interceptor;

import com.example.tukgraduation.global.annotation.LoginRequired;
import com.example.tukgraduation.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isLoginRequest(handler) && !loginService.isUserLogin(request.getSession())) {
            throw new Exception(); //추후 수정
        }
        return true;
    }

    private boolean isLoginRequest(Object handler) {
        return handler instanceof HandlerMethod
                && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class);
    }

}
