package com.example.tukgraduation.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImageService {
    String upload(String username, MultipartFile multipartFile);
}