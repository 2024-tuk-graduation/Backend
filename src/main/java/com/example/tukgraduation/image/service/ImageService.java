package com.example.tukgraduation.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    String upload(String username, MultipartFile multipartFile);
}