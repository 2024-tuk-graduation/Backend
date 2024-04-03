package com.example.tukgraduation.UploadFile.service;

import com.example.tukgraduation.UploadFile.domain.UploadFile;
import com.example.tukgraduation.UploadFile.repository.UploadFileRepository;
import com.example.tukgraduation.chatroom.domain.Room;
import com.example.tukgraduation.chatroom.dto.RoomCreateResponse;
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
public class UploadFileService {
    private final UploadFileRepository uploadFileRepository;
    private final S3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public UploadFileService(UploadFileRepository uploadFileRepository, S3Client amazonS3Client) {
        this.uploadFileRepository = uploadFileRepository;
        this.amazonS3Client = amazonS3Client;
    }

    @Transactional
    public RoomCreateResponse uploadAndSaveUploadFiles(List<MultipartFile> files, Room room) {


        List<String> pdfUrls = new ArrayList<>();
        List<String> codeUrls = new ArrayList<>();


        if (files == null || files.isEmpty()) {
            // 파일이 없어도 방 생성 정보를 반환할 수 있도록 RoomCreateResponse 객체를 생성합니다.
            return new RoomCreateResponse(room, null, null);
        }

        List<UploadFile> savedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = room.getRoomName() + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String fileType = file.getContentType(); // 파일 타입을 구분하기 위해 파일의 MIME 타입을 가져옵니다.

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

                if ("application/pdf".equals(fileType)) {
                    pdfUrls.add(fileUrl);
                } else if ("application/octet-stream".equals(fileType)) {
                    codeUrls.add(fileUrl);
                }

                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setFileType(file.getContentType());
                uploadFile.setFileUrl(fileUrl);
                uploadFile.setRoomId(room.getId());
                uploadFileRepository.save(uploadFile);

                savedFiles.add(uploadFile);
            } catch (IOException | AwsServiceException e) {
                // 적절한 예외 처리를 여기서 진행
                e.printStackTrace();
            }
        }

        return new RoomCreateResponse(room, pdfUrls, codeUrls);
    }

//    private String getFileUrl(String fileName) {
//        // 버킷과 객체 키를 기반으로 S3 객체의 공개 URL을 생성하는 로직
//        // 참고: 실제 환경에서는 생성된 객체 URL에 대한 액세스 권한이 필요할 수 있음
//        return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
//    }
}
