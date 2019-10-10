package me.tianjx98.blog.bean;

import lombok.Data;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName Markdown
 * @Author tianjx98
 * @Date 2019-10-01 23:37
 */
@Data
public class Markdown {
    String title;
    List<String> tags;
    List<String> categories;
    String date;
    String content;

    public String getDate() {
        if (date != null) return date;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public String getContent() {
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder(content);
        for (int i = 0; i < 30; i++) {
            sb.append(lineSeparator);
        }
        content = sb.toString();
        return content;
    }

    /**
     * 将markdown对象转化为字符串,可以直接存储在md文档中
     *
     * @param markdown md对象
     * @return md字符串
     */
    public static String toString(Markdown markdown) {
        StringBuilder builder = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        builder.append("---");
        builder.append(lineSeparator);

        builder.append("title: ");
        builder.append(markdown.getTitle());
        builder.append(lineSeparator);

        builder.append("date: ");
        builder.append(markdown.getDate());
        builder.append(lineSeparator);

        builder.append("tags:");
        builder.append(lineSeparator);
        List<String> tags = markdown.getTags();
        if (tags != null) {
            for (String tag : tags) {
                builder.append("- ");
                builder.append(tag);
                builder.append(lineSeparator);
            }
        }

        builder.append("categories:");
        builder.append(lineSeparator);
        List<String> categories = markdown.getCategories();
        if (categories != null) {
            for (String category : categories) {
                builder.append("- ");
                builder.append(category);
                builder.append(lineSeparator);
            }
        }
        builder.append("---");
        builder.append(lineSeparator);

        builder.append(markdown.getContent());
        return builder.toString();
    }


    private static final String PREFIX_TITLE = "title:";
    private static final String PREFIX_TAGS = "tags:";
    private static final String PREFIX_CATEGORIES = "categories:";
    private static final String PREFIX_DATE = "date:";

    /**
     * 将markdown字符串转化为对象
     *
     * @param mdAbsolutePath md文档的绝对路径
     * @return markdown文档对象
     */
    public static Markdown of(String mdAbsolutePath) {
        // 获取标题
        Markdown markdown = new Markdown();
        List<String> list = null;
        File file = new File(mdAbsolutePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // 如果出现---并且title不为空, 说明头信息读取完了
                if ("---".equals(line)) {
                    if (markdown.getTitle() != null) {
                        if (markdown.getTags() == null) markdown.setTags(list);
                        if (markdown.getCategories() == null) markdown.setCategories(list);
                        break;
                    }
                }
                // 获取标题
                if (line.startsWith(PREFIX_TITLE)) {
                    markdown.setTitle(line.substring(PREFIX_TITLE.length()).trim());
                }
                // 获取日期
                if (line.startsWith(PREFIX_DATE)) {
                    markdown.setDate(line.substring(PREFIX_DATE.length()).trim());
                }
                //标记tags
                if (line.startsWith("- ")) {
                    if (list != null) {
                        list.add(line.substring(2).trim());
                    }
                } else if (line.startsWith(PREFIX_TAGS)) {
                    markdown.setCategories(list);
                    list = new LinkedList<>();
                } else if (line.startsWith(PREFIX_CATEGORIES)) {
                    markdown.setTags(list);
                    list = new LinkedList<>();
                }
            }
            StringBuilder mdContent = new StringBuilder();
            String lineSeparator = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                mdContent.append(line);
                mdContent.append(lineSeparator);
            }
            markdown.setContent(mdContent.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return markdown;
    }
}
