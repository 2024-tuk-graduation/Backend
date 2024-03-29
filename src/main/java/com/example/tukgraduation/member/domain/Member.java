package com.example.tukgraduation.member.domain;

import com.example.tukgraduation.global.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET is_deleted = NOW() WHERE id = ?")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, length = 20)
    private String username;

    @NotBlank
    @Column(length = 100)
    private String password;

    @NotBlank
    @Column(length = 20)
    private String nickname;

    @Getter
    @Column(name = "profile_image_url")
    private String profileImageUrl;

}
