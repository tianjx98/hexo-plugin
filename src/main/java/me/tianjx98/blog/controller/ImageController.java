package me.tianjx98.blog.controller;

import me.tianjx98.blog.bean.ImageUploadMessage;
import me.tianjx98.blog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName ImageController
 * @Author tianjx98
 * @Date 2019-09-24 14:52
 */
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @RequestMapping("/upload")
    public ImageUploadMessage upload(@RequestParam("editormd-image-file") MultipartFile multipartFile) {
        return imageService.upload(multipartFile);
    }
}
