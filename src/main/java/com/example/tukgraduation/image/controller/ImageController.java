package com.example.tukgraduation.image.controller;

import com.example.tukgraduation.global.annotation.LoginMember;
import com.example.tukgraduation.global.result.ResultCode;
import com.example.tukgraduation.global.result.ResultResponse;
import com.example.tukgraduation.image.service.ImageService;
import com.example.tukgraduation.member.dto.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(summary = "이미지 업로드", description = "이미지를 넘겨서 업로드")
    @PostMapping( value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ResultResponse<String>> upload(@LoginMember AuthInfo authInfo, @RequestParam MultipartFile multipartFile) {
        String imageUrl = imageService.upload(authInfo.getUsername(), multipartFile);
        ResultResponse<String> response = new ResultResponse<>(ResultCode.UPLOAD_SUCCESS, imageUrl);
        // 수정 필요
        return ResponseEntity.ok().body(response);
    }
}