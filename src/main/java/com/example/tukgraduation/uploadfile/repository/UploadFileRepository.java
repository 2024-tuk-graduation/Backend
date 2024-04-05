package com.example.tukgraduation.uploadfile.repository;

import com.example.tukgraduation.uploadfile.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}