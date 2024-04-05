package com.example.tukgraduation.uploadfile.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Length(max = 2048)
    private String fileUrl;

    private Long roomId; // 방 ID를 저장하여 어떤 방의 파일인지 참조

    // 생성자, Getter 및 Setter 생략
}
