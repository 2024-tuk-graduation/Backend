package com.example.tukgraduation.member.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
public class MemberDto {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder
    public static class MemberCreateRequest {

        @NotEmpty
        @Length(min = 2, max = 20)
        private String username;

        @NotEmpty
        @Length(min = 4, max = 20)
        private String password;


    }


}
