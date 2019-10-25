package me.tianjx98.blog.utils;

import me.tianjx98.blog.bean.Categorie;
import me.tianjx98.blog.bean.Markdown;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName Test
 * @Author tianjx98
 * @Date 2019-10-01 18:47
 */

public class Test {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return 1;
        int len = 1;
        int maxLen = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                len++;
                if (len > maxLen) maxLen = len;
            } else {
                len = 1;
            }
        }
        return maxLen;
    }

    public static void main(String[] args) throws IOException {
        int lengthOfLCIS = new Test().findLengthOfLCIS(new int[]{1, 3, 5, 4, 7});
        System.out.println(lengthOfLCIS);
    }
}
