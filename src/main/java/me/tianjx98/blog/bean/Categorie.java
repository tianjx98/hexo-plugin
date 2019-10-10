package me.tianjx98.blog.bean;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName Categorie
 * @Author tianjx98
 * @Date 2019-10-01 16:26
 */
@Data
public class Categorie {
    private String name;
    private List<Categorie> subs = new LinkedList<>();

    public Categorie(String name) {
        this.name = name;
    }
    public void addChild(Categorie child) {
        subs.add(child);
    }
}
