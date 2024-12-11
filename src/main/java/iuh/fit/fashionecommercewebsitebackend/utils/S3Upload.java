package iuh.fit.fashionecommercewebsitebackend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletionException;

@Component
@RequiredArgsConstructor
public class S3Upload {

    private final S3TransferManager transferManager;

    @Value("${aws-s3.bucket-name}")
    private String bucketName;
//
//    public String uploadFile(MultipartFile multipartFile) throws IOException {
//        Path tempFile = Files.createTempFile("temp", multipartFile.getOriginalFilename());
//        Files.copy(multipartFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
//
//        String key = generateFileName(multipartFile.getOriginalFilename());
//        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
//                .putObjectRequest(b -> b.bucket(bucketName).key(key))
//                .source(tempFile)
//                .build();
//        FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);
//        fileUpload.completionFuture().join();
//        return "https://" + bucketName + ".s3." + "ap-southeast-1" + ".amazonaws.com/" + key;
//    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Path tempFile = Files.createTempFile("temp", multipartFile.getOriginalFilename());
        String key = generateFileName(multipartFile.getOriginalFilename());

        try {
            Files.copy(multipartFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Tạo request để upload file
            UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                    .putObjectRequest(b -> b.bucket(bucketName).key(key))
                    .source(tempFile)
                    .build();
            FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);

            // Chờ hoàn thành và xử lý lỗi
            try {
                fileUpload.completionFuture().join();
            } catch (CompletionException e) {
                throw new RuntimeException("Failed to upload file to S3", e);
            }

        } finally {
            Files.deleteIfExists(tempFile); // Xóa file tạm để giải phóng tài nguyên
        }

        return "https://" + bucketName + ".s3." + "ap-southeast-1" + ".amazonaws.com/" + key;
    }


    private String generateFileName(String originalFileName) {
        return LocalDateTime.now().toString() + "_" + UUID.randomUUID().toString() + "_" + originalFileName;
    }
}
