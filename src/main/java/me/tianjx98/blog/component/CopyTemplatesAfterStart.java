package me.tianjx98.blog.component;

import me.tianjx98.blog.utils.FileReadWriteUtils;
import me.tianjx98.blog.utils.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * @ClassName DoSomethingAfterStart
 * @Author tianjx98
 * @Date 2019-09-26 20:05
 */
@Component
public class CopyTemplatesAfterStart implements ApplicationRunner {
    @Value("${filePath.hexoPublic}")
    private String hexoPublic;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取所有文件名
        //InputStream stream = this.getClass().getResourceAsStream("/templates/");
        //String[] files = FileReadWriteUtils.read(stream).split(System.getProperty("line.separator"));
        String[] files = {"article.html", "editArticle.html", "editor.html", "fragments.html", "login.html", "newArticle.html", "others.html"};
        // 将文件内容复制到目标文件夹
        for (String file : files) {
            try (InputStream inputStream = this.getClass().getResourceAsStream("/templates/" + file);
                 OutputStream outputStream = new FileOutputStream(hexoPublic + file)) {
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
            }
        }
    }
}
