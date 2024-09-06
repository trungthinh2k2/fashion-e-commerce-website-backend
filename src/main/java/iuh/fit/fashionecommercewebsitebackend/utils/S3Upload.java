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

@Component
@RequiredArgsConstructor
public class S3Upload {

    private final S3TransferManager transferManager;

    @Value("${aws-s3.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Path tempFile = Files.createTempFile("temp", multipartFile.getOriginalFilename());
        Files.copy(multipartFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        String key = generateFileName(multipartFile.getOriginalFilename());
        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                .putObjectRequest(b -> b.bucket(bucketName).key(key))
                .source(tempFile)
                .build();
        FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);
        fileUpload.completionFuture().join();
        return "https://" + bucketName + ".s3." + "ap-southeast-1" + ".amazonaws.com/" + key;
    }

    private String generateFileName(String originalFileName) {
        return LocalDateTime.now().toString() + "_" + UUID.randomUUID().toString() + "_" + originalFileName;
    }
}
