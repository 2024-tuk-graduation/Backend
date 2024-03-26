package com.example.tukgraduation.member.service;

import com.example.tukgraduation.member.domain.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.tukgraduation.global.constant.Attribute.USER_ID;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;

    public void login(Long id, HttpSession session) {
        session.removeAttribute(USER_ID);
        session.setAttribute(USER_ID, id);
    }

    public void logout(HttpSession session) {
        session.removeAttribute(USER_ID);
    }

    public boolean checkPassword(String username, String checkPassword) {
        Member member = memberService.findUserByUsername(username);
        String realPassword = member.getPassword();
        return realPassword.matches(checkPassword);
    }
    public Member getLoginUser(HttpSession session) {
        Long userId = getLoginUserId(session);
        return memberService.findUserById(userId);
    }
    public Long getLoginUserId(HttpSession session) {
        return (Long) session.getAttribute(USER_ID);
    }

    public boolean isUserLogin(HttpSession session) {
        return getLoginUser(session) != null;
    }
}
