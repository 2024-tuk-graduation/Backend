package com.example.tukgraduation.pdf.service;

import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.pdf.domain.PdfFile;
import com.example.tukgraduation.pdf.repository.PdfFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfFileService {
    private final PdfFileRepository pdfFileRepository;
    private final S3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PdfFileService(PdfFileRepository pdfFileRepository, S3Client amazonS3Client) {
        this.pdfFileRepository = pdfFileRepository;
        this.amazonS3Client = amazonS3Client;
    }

    @Transactional
    public List<PdfFile> uploadAndSavePdfFiles(List<MultipartFile> files, Room room) {

        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }

        List<PdfFile> savedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = room.getRoomName() + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build();

                RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
                amazonS3Client.putObject(putObjectRequest, requestBody);
                String fileUrl = amazonS3Client.utilities().getUrl(GetUrlRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build()).toString();


                PdfFile pdfFile = new PdfFile();
                pdfFile.setFileName(file.getOriginalFilename());
                pdfFile.setFileUrl(fileUrl);
                pdfFile.setRoomId(room.getId());
                pdfFileRepository.save(pdfFile);

                savedFiles.add(pdfFile);
            } catch (IOException | AwsServiceException e) {
                // 적절한 예외 처리를 여기서 진행
                e.printStackTrace();
            }
        }

        return savedFiles;
    }

//    private String getFileUrl(String fileName) {
//        // 버킷과 객체 키를 기반으로 S3 객체의 공개 URL을 생성하는 로직
//        // 참고: 실제 환경에서는 생성된 객체 URL에 대한 액세스 권한이 필요할 수 있음
//        return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
//    }
}
