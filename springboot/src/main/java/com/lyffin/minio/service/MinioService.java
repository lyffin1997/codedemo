package com.lyffin.minio.service;

import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface MinioService {

    /**
     * 列出存储桶中的所有对象名称
     * @param bucketName
     * @return
     */
    List<String> listObjectNames(String bucketName);

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param bucketName
     */
    String putObject(MultipartFile multipartFile, String bucketName, String fileType);

    /**
     * 文件流下载
     * @param bucketName
     * @param objectName
     * @return
     */
    InputStream downloadObject(String bucketName, String objectName);
}
