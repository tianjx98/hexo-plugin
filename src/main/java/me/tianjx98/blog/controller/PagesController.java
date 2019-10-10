package me.tianjx98.blog.controller;

import me.tianjx98.blog.bean.MsgResponse;
import me.tianjx98.blog.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName PagesController
 * @Author tianjx98
 * @Date 2019-09-25 20:13
 */
@Controller
@RequestMapping("/")
public class PagesController {

    @Value("${password}")
    String password;


    @GetMapping("login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @PostMapping("loginProcess")
    public MsgResponse loginProcess(HttpServletRequest request, String password) {
        if (password.equals(this.password)) {
            HttpSession session = request.getSession(true);
            return MsgResponse.success("登录成功!", true);
        }
        return MsgResponse.success("密码错误!", false);
    }

    /**
     * 映射主页
     */
    @RequestMapping("")
    public ModelAndView index(HttpServletRequest request) {
        return getModelAndView(request);
    }

    @RequestMapping("tags")
    public ModelAndView tags(HttpServletRequest request) {
        return getModelAndView(request);
    }

    @RequestMapping("tags/{detail}")
    public ModelAndView tagsDetail(HttpServletRequest request) {
        return getModelAndView(request);
    }

    @RequestMapping("categories/**")
    public ModelAndView categories(HttpServletRequest request) {
        return getModelAndView(request);
    }

    //@RequestMapping("categories/")
    //public ModelAndView categoriesDetail(HttpServletRequest request) {
    //    return getModelAndView(request);
    //}

    @RequestMapping("archives")
    public ModelAndView archives(HttpServletRequest request) {
        return getModelAndView(request);
    }

    /**
     * 归档分页映射
     *
     * @param request
     * @return
     */
    @RequestMapping("archives/page/*")
    public ModelAndView archivesPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String uri = request.getRequestURI();
        modelAndView.addObject("uri", uri + "index");
        modelAndView.setViewName("others");
        return modelAndView;
    }

    /**
     * 获取文章页面
     */
    @RequestMapping("20{year}/{month}/{day}/{name}")
    public ModelAndView archivesDetail(HttpServletRequest request,
                                       @PathVariable("year") int year,
                                       @PathVariable("month") int month,
                                       @PathVariable("day") int day,
                                       @PathVariable("name") String name) {
        ModelAndView modelAndView = getModelAndView(request);
        modelAndView.addObject("name", name);
        modelAndView.setViewName("article");
        return modelAndView;
    }

    @Autowired
    MarkdownService markdownService;

    /**
     * 文章编辑页面
     */
    @RequestMapping("edit/{name}")
    public ModelAndView edit(@PathVariable("name") String name, String uri) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", name);
        //modelAndView.addObject("content", markdownService.read(name));
        //modelAndView.addObject("uri", uri);
        modelAndView.setViewName("editArticle");
        return modelAndView;
    }

    /**
     * 创建新文章页面
     */
    @RequestMapping("new")
    public ModelAndView newArticle() {
        ModelAndView modelAndView = new ModelAndView();
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            sb.append(lineSeparator);
        }
        modelAndView.addObject("content", sb.toString());
        modelAndView.setViewName("newArticle");
        return modelAndView;
    }

    private ModelAndView getModelAndView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String uri = request.getRequestURI();
        modelAndView.addObject("uri", uri.substring(1) + "index");
        modelAndView.setViewName("others");
        return modelAndView;
    }
}
