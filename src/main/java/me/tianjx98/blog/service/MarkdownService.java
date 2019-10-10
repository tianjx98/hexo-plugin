package me.tianjx98.blog.service;

import me.tianjx98.blog.bean.Categorie;
import me.tianjx98.blog.bean.Markdown;

import java.util.List;

public interface MarkdownService {
    String read(String name);

    void write(String name, String content);

    List<String> getAllTags();

    List<String> getSubCategories(String name);

    boolean containsTitle(String title);

    void save(Markdown markdown);

    Markdown readAsMarkdown(String name);
}
