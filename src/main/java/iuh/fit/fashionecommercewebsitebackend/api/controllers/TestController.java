package iuh.fit.fashionecommercewebsitebackend.api.controllers;

import iuh.fit.fashionecommercewebsitebackend.utils.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final S3Upload s3Upload;

    @PostMapping("/upload-s3")
    public String testUploadS3(@ModelAttribute MultipartFile file) {
        try {
            s3Upload.uploadFile(file);
        } catch (Exception e) {
            return "Upload S3 failed!";
        }
        return "Upload S3 successfully!";
    }
}
