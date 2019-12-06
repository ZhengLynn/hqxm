package com.baizhi.backcontroller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.baizhi.util.FileURLUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/11/28  18:39
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        return articleService.findAll(page, rows);
    }

    @RequestMapping("operation")
    public Map operation(HttpServletRequest request, String oper, String[] id, Article article, String sign, MultipartFile imgFile) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            String s = UUID.randomUUID().toString().replace("-", "");
            article.setId(s);
            map = articleService.save(article);
            upload(imgFile, request, s, sign);
        } else if (oper.equals("edit")) {
            map = articleService.update(article);
        } else if (oper.equals("del")) {
            map = articleService.delete(id);
        }
        return map;
    }

    @RequestMapping("upload")
    public Map upload(MultipartFile imgFile, HttpServletRequest request, String articleId, String sign) {
        Map map = new HashMap();
        if (imgFile.getOriginalFilename().length() == 0 || imgFile.getOriginalFilename() == null) {
            map.put("status", "400");
            map.put("msg", "文件为空");
            return map;
        }
        String dir = null;
        if (sign == null) {
            dir = "/static/img/article/contentImg/";
        } else {
            dir = "/static/img/article/";
        }
        String URL = null;
        try {
            URL = FileURLUtil.getURL(request, imgFile, dir);
            map.put("error", 0);
            map.put("url", URL);
        } catch (Exception e) {
            map.put("error", 1);
            map.put("message", "上传错误");
            e.printStackTrace();
        }
        //URL存入数据库
        Article article = new Article();
        article.setId(articleId).setCover(URL);
        articleService.update(article);

        map.put("status", "200");
        map.put("msg", "上传成功");

        return map;
    }

    //KindEditor图片空间
    @RequestMapping("showAllImgs")
    public Map showAllImgs(HttpSession session, HttpServletRequest request) {
        //1. 获取绝对路径
        String realPath = session.getServletContext().getRealPath("/static/img/article/contentImg");
        //2. 准备返回的Json数据
        Map map = new HashMap();
        List list = new ArrayList();
        //3. 获取目标文件夹 拿到文件夹中所有的文件
        File file = new File(realPath);
        File[] files = file.listFiles();
        //4. 遍历所有文件
        for (File f : files) {
            //5. 文件属性的封装
            Map fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("filesize", f.length());
            fileMap.put("is_photo", true);
            //文件后缀、文件名
            String extension = FilenameUtils.getExtension(f.getName());
            fileMap.put("filetype", extension);
            fileMap.put("filename", f.getName());
            //文件上传时间
            String s = f.getName().split("_")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime", format);
            list.add(fileMap);
        }
        map.put("file_list", list);
        map.put("total_count", list.size());
        //项目名+存储文件夹路径
        map.put("current_url", request.getContextPath() + "/static/img/article/contentImg/");
        return map;
    }
}
