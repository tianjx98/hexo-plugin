package me.tianjx98.blog.service.impl;

import me.tianjx98.blog.bean.ImageUploadMessage;
import me.tianjx98.blog.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * @ClassName ImageServiceImpl
 * @Author tianjx98
 * @Date 2019-09-24 15:56
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${filePath.hexoSource}")
    private String hexoSource;

    @Override
    public ImageUploadMessage upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        File saveFile = Paths.get(hexoSource, "images/", originalFilename).toFile();
        File parent = saveFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(saveFile)) {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            InputStream inputStream = file.getInputStream();
            byte[] bytes = new byte[4096];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            return ImageUploadMessage.builder()
                    .success(1)
                    .message(originalFilename+"上传成功!")
                    .url("/images/"+originalFilename)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return ImageUploadMessage.builder()
                    .success(0)
                    .message("上传失败:" + e.getMessage())
                    .build();
        }
    }
}
