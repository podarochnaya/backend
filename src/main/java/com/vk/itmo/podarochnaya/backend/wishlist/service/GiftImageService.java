package com.vk.itmo.podarochnaya.backend.wishlist.service;

import com.vk.itmo.podarochnaya.backend.exception.GiftImageException;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.FileDto;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftImageService {


    private final MinioClient minioClient;
    private String bucketName = "gift-images";

    @PostConstruct
    public void initializeBucket() {
        try {
            // Проверка, существует ли корзина
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                log.info("Bucket '{}' already exists.", bucketName);
            } else {
                // Если не существует, создаем корзину
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket '{}' created successfully.", bucketName);
            }
        } catch (MinioException e) {
            log.info("Error while checking/creating the bucket: {}", e.getMessage());
        } catch (Exception e) {
            log.info("General error: {}", e.getMessage());
        }
    }

    public String uploadGiftImage(FileDto file) {
        String fileName = UUID.randomUUID() + "-" + file.getFileName();

        byte[] fileContent = file.getFileContent();

        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(new ByteArrayInputStream(fileContent), fileContent.length, -1)
                    .contentType(file.getContentType())
                    .build()
            );
        } catch (Exception e) {
            throw new GiftImageException("Could not process image:", e);
        }

        return fileName;
    }

    public InputStream getGiftImage(String fileName) {
        try {
            return minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
        } catch (Exception e) {
            throw new GiftImageException("Could not process image:", e);
        }
    }

    public void deleteGiftImage(String fileName) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
        } catch (Exception e) {
            throw new GiftImageException("Could not process image:", e);
        }
    }

    public boolean imageExists(String fileName) {
        try {
            minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
            return true;
        } catch (ErrorResponseException e) {
            return false;
        } catch (Exception e) {
            throw new GiftImageException("Could not process image:", e);
        }
    }

    public byte[] getFileAsBytes(String photoUrl) {
        try (InputStream inputStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(bucketName)
                .object(photoUrl)
                .build()
        )) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new GiftImageException("Could not process image:", e);
        }
    }
}
