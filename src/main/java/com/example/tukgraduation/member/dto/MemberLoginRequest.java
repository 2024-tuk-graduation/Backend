package com.example.tukgraduation.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberLoginRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
