package com.WalkiePaw.presentation.domain.upload;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class UploadService {

    private final AmazonS3 amazonS3;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final String region;
    public UploadService(
            @Value("${cloud.aws.credentials.accessKey}") String accessKey,
            @Value("${cloud.aws.credentials.secretKey}") String secretKey,
            @Value("${cloud.aws.s3.bucketName}") String bucketName,
            @Value("${cloud.aws.region.static}") String region) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.region = region;
    }

    public String upload(MultipartFile file) {
        String oriName = file.getOriginalFilename();
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, oriName, file.getInputStream(), null));
            return amazonS3.getUrl(bucketName, oriName).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
