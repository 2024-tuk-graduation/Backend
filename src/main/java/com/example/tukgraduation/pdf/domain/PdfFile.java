package com.example.tukgraduation.pdf.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PdfFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    @Length(max = 2048)
    private String fileUrl;

    private Long roomId; // 방 ID를 저장하여 어떤 방의 파일인지 참조

    // 생성자, Getter 및 Setter 생략
}
