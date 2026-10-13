package com.example.marketplace.global.util.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class S3Storage implements Storage {

    @Value("${storage.s3.end-point}")
    private String endPoint;

    @Value("${storage.s3.region-name}")
    private String regionName;

    @Value("${storage.s3.access-key}")
    private String accessKey;

    @Value("${storage.s3.secret-key}")
    private String secretKey;

    @Value("${storage.s3.bucket-name}")
    private String bucketName;

    @Override
    public void saveMultipartFile(MultipartFile multipartFile, String fileName){
        try {
            File file = convertToFile(multipartFile);
            saveFile(file, fileName);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("S3 API request failed.", e);
        } catch (Exception e){
            throw new RuntimeException("Failed to save multipart file.", e);
        }
    }

    @Override
    public void saveFile(File file, String fileName){
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        s3.putObject(bucketName, fileName, file);
    }

    private File convertToFile(MultipartFile multipartFile) {
        try {
            File tempFile = File.createTempFile("temp", null, null);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(multipartFile.getBytes());
                return tempFile;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert multipart file.", e);
        }
    }
}
