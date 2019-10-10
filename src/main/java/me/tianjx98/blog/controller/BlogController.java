package me.tianjx98.blog.controller;

import me.tianjx98.blog.bean.Categorie;
import me.tianjx98.blog.bean.Markdown;
import me.tianjx98.blog.bean.MsgResponse;
import me.tianjx98.blog.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName BlogController
 * @Author tianjx98
 * @Date 2019-09-25 20:37
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    MarkdownService markdownService;

    @PostMapping("/save/{year}/{month}/{day}/{name}/index")
    public ModelAndView save(HttpServletRequest request,
                             @PathVariable("name") String name,
                             String content) {
        if (content != null && !"".equals(content)) {
            System.out.println("save");
            markdownService.write(name, content);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("uri", request.getRequestURI().substring(5));
        modelAndView.setViewName("article");
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/save/markdown")
    public MsgResponse save(@RequestBody Markdown markdown) {
        markdownService.save(markdown);
        return MsgResponse.success("保存成功!", null);
    }

    @ResponseBody
    @GetMapping("get/{name}")
    public Markdown getMarkdown(@PathVariable("name") String name) {
        return markdownService.readAsMarkdown(name);
    }

    @ResponseBody
    @GetMapping("/containsTitle")
    public MsgResponse containsTitle(String title) {

        return MsgResponse.success("标题已经存在!", markdownService.containsTitle(title));
    }

    @ResponseBody
    @GetMapping("/allTags")
    public List<String> getAllTags() {
        return markdownService.getAllTags();
    }

    @ResponseBody
    @GetMapping("/allCategories")
    public List<String> getAllCategories(String name) {
        return markdownService.getSubCategories(name);
    }
}
