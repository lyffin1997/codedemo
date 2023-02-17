package com.lyffin.minio.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyffin.minio.common.FileTypeUtils;
import com.lyffin.minio.common.MinioUtils;
import com.lyffin.minio.service.MinioService;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@Slf4j
public class MinioController {

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file, String bucketName) {
        String fileType = FileTypeUtils.getFileType(file);
        if (fileType != null) {
            return minioService.putObject(file, bucketName, fileType);
        }
        return "不支持的文件格式。请确认格式,重新上传！！！";
    }

    @GetMapping("/upload1")
    public String upload() throws Exception {
        minioUtils.putObject("test", "/txt/test.txt", "/Users/lyffin/Downloads/product-mapping.txt");
        return "success";
    }


    @GetMapping("/showListObjectNameAndDownloadUrl/{bucketName}")
    public Map<String, String> showListObjectNameAndDownloadUrl(@PathVariable String bucketName) {
        Map<String, String> map = new HashMap<>();
        List<String> listObjectNames = minioService.listObjectNames(bucketName);
        String url = "localhost:8085/minio/download/" + bucketName + "/";
        listObjectNames.forEach(System.out::println);
        for (int i = 0; i <listObjectNames.size() ; i++) {
            map.put(listObjectNames.get(i),url+listObjectNames.get(i));
        }
        return map;
    }


    @RequestMapping("/download/{bucketName}/{objectName}")
    public void download(HttpServletResponse response, @PathVariable("bucketName") String bucketName, @PathVariable("objectName") String objectName) {
        InputStream in = null;
        try {
            in = minioService.downloadObject(bucketName, objectName);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(objectName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            //将字节从InputStream复制到OutputStream 。
            IOUtils.copy(in, response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
