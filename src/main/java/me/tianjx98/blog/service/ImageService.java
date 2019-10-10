package me.tianjx98.blog.service;

import me.tianjx98.blog.bean.ImageUploadMessage;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageUploadMessage upload(MultipartFile file);

}
