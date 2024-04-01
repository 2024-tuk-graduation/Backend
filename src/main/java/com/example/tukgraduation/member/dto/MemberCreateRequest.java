package com.example.tukgraduation.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Data
@NoArgsConstructor
@Setter
public class MemberCreateRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotBlank
    private String nickname;

    public MemberCreateRequest(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}