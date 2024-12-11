package iuh.fit.fashionecommercewebsitebackend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import static software.amazon.awssdk.transfer.s3.SizeConstant.MB;

@Configuration
public class S3Config {

    @Value("${aws-s3.access-key}")
    private String accessKey;
    @Value("${aws-s3.secret-key}")
    private String secretKey;
    @Value("${aws-s3.region}")
    private String region;

    @Bean
    public S3TransferManager s3TransferManager() {

        AwsCredentialsProvider credentialsProvider = () -> AwsBasicCredentials.create(accessKey, secretKey);

        S3AsyncClient s3AsyncClient = S3AsyncClient.crtBuilder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(region))
                .targetThroughputInGbps(20.0)
                .minimumPartSizeInBytes(8 * MB)
                .build();

        return S3TransferManager.builder()
                .s3Client(s3AsyncClient)
                .build();
    }

}
