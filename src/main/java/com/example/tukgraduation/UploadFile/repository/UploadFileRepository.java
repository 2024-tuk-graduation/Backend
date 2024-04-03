package com.example.tukgraduation.UploadFile.repository;

import com.example.tukgraduation.UploadFile.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}