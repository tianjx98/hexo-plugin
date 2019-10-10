package me.tianjx98.blog.utils;

import java.io.*;

/**
 * @ClassName FileReadWriteUtils
 * @Author tianjx98
 * @Date 2019-10-01 17:07
 */
public class FileReadWriteUtils {
    public static String read(String absolutePath) throws IOException {
        File file = new File(absolutePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder mdContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                mdContent.append(line);
                mdContent.append("\n");
            }
            return mdContent.toString();
        }
    }

    public static String read(InputStream inputStream) {
        int len;
        StringBuilder sb = new StringBuilder();
        char[] chars = new char[1024];
        try (InputStreamReader reader=new InputStreamReader(inputStream)){
            while ((len = reader.read(chars)) != -1) {
                sb.append(chars, 0, len);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void write(String absolutePath, String content) {
        File file = new File(absolutePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
