package me.tianjx98.blog.service.impl;

import me.tianjx98.blog.bean.Categorie;
import me.tianjx98.blog.bean.Markdown;
import me.tianjx98.blog.service.MarkdownService;
import me.tianjx98.blog.utils.FileReadWriteUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName MarkdownServiceImpl
 * @Author tianjx98
 * @Date 2019-09-27 15:48
 */
@Service
public class MarkdownServiceImpl implements MarkdownService {
    @Value("${filePath.hexoSource}")
    String hexoSource;
    @Value("${filePath.hexoPublic}")
    String hexoPublic;

    /**
     * 根据文章标题获取文章字符串
     *
     * @param name 文章标题
     * @return md文档字符串
     */
    @Override
    public String read(String name) {
        try {
            FileReadWriteUtils.read(hexoSource + name + ".md");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将md文档字符串写入到文件中
     *
     * @param name    文章标题
     * @param content md文档字符串
     */
    @Override
    public void write(String name, String content) {
        FileReadWriteUtils.write(hexoSource + name + ".md", content);
    }

    /**
     * 获取博客所有的标签
     *
     * @return 所有标签的集合
     */
    @Override
    public List<String> getAllTags() {
        try {
            String html = FileReadWriteUtils.read(hexoPublic + "tags/index.html");
            Document doc = Jsoup.parse(html);
            Elements select = doc.select("#posts > div > div > div > div.tag-cloud-tags > a");
            LinkedList<String> tags = new LinkedList<>();
            for (Element element : select) {
                tags.add(element.text());
            }
            return tags;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据博客分类获取下面的子分类
     *
     * @param name 主分类
     * @return 子分类的集合
     */
    @Override
    public List<String> getSubCategories(String name) {
        try {
            String html = FileReadWriteUtils.read(hexoPublic + "categories/index.html");
            Document doc = Jsoup.parse(html);
            Element select = doc.select("#posts > div > div > div > div.category-all > ul").first();
            Categorie categories = new Categorie("head");
            extractCategrie(select, categories);

            ArrayList<String> list = new ArrayList<>();
            find(categories, name, list);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从所有分类中获取某个分类下面的子分类
     *
     * @param categories
     * @param name
     * @param list
     */
    private void find(Categorie categories, String name, ArrayList<String> list) {
        if (categories.getName().equals(name)) {
            for (Categorie sub : categories.getSubs()) {
                list.add(sub.getName());
            }
        }
        for (Categorie sub : categories.getSubs()) {
            find(sub, name, list);
        }
    }

    /**
     * 查看文章标题是否存在
     *
     * @param title 文章标题
     * @return 如果文章标题已经存在返回true, 否则返回false
     */
    @Override
    public boolean containsTitle(String title) {
        File file = new File(hexoSource);
        String[] list = file.list();
        HashSet<String> fileNames = new HashSet<>();
        for (String name : list) {
            fileNames.add(name.substring(0, name.length() - 3));
        }
        return fileNames.contains(title);
    }

    /**
     * 存储markdown文档对象
     *
     * @param markdown md文档对象
     */
    @Override
    public void save(Markdown markdown) {
        write(markdown.getTitle(), Markdown.toString(markdown));
    }

    @Override
    public Markdown readAsMarkdown(String name) {
        return Markdown.of(hexoSource + name + ".md");
    }


    /**
     * 递归获取所有分类
     */
    private void extractCategrie(Element element, Categorie head) {
        for (Element item : element.select("> .category-list-item")) {
            Categorie categorie = new Categorie(item.select("> a").text());
            head.addChild(categorie);
            for (Element child : item.select("> .category-list-child")) {
                extractCategrie(child, categorie);
            }
        }
    }

}
