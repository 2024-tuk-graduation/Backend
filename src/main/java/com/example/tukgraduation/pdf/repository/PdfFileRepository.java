package com.example.tukgraduation.pdf.repository;

import com.example.tukgraduation.pdf.domain.PdfFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfFileRepository extends JpaRepository<PdfFile, Long> {
}