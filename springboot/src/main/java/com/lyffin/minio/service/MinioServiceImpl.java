package com.lyffin.minio.service;

import com.lyffin.minio.common.MinioProperties;
import com.lyffin.minio.common.MinioUtils;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioUtils minioUtils;
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProperties minioProperties;

    @Override
    public List<String> listObjectNames(String bucketName) {
        return minioUtils.listObjectNames(bucketName);
    }


    @Override
    public String putObject(MultipartFile file, String bucketName, String fileType) {
        try {
            bucketName = StringUtils.hasLength(bucketName) ? bucketName : minioProperties.getBucketName();
            if (!minioUtils.bucketExists(bucketName)) {
                minioUtils.createBucket(bucketName);
            }
            String fileName = file.getOriginalFilename();

            String objectName = UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));
            minioUtils.putObject(bucketName, file, objectName, fileType);
            return minioProperties.getEndpoint() + "/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    @Override
    public InputStream downloadObject(String bucketName, String objectName) {
        InputStream inputStream = null;
        try {
            inputStream = minioUtils.getObject(bucketName, objectName);
        }catch (Exception e) {
            return null;
        }
        return inputStream;
    }
}
