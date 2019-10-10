package me.tianjx98.blog.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName ImageUploadMessage
 * @Author tianjx98
 * @Date 2019-09-24 14:56
 */
@Data
@Builder
public class ImageUploadMessage {
    /**
     * 0 表示上传失败，1 表示上传成功
     */
    int success;
    /**
     * 提示信息
     */
    String message;
    /**
     * 图片地址, 上传成功时才返回
     */
    String url;
}
