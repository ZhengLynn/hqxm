package com.baizhi.frontcontroller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  11:19
 */
@RestController
@RequestMapping("fArticle")
public class FrontArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;

    //一级页面
    @RequestMapping("stairPage")
    public Map stairPage(String uid, String type, String sub_type) {
        Map map = new HashMap();
        List<Banner> head = new ArrayList<>();
        List<Article> articles = new ArrayList<>();
        List<Album> albums = new ArrayList<>();
        try {
            if (type.equals("all")) {
                head = bannerService.findByRand();
                articles = articleService.findByReleaseDate();
                albums = albumService.findByReleaseDate();
            }
            if (type.equals("wen")) {
                albums = (List<Album>) albumService.findAll(0, Integer.MAX_VALUE).get("rows");
            }
            if (type.equals("si")) {
                String[] guru_ids = null;
                if (sub_type.equals("ssyj")) {
                    guru_ids = new String[]{"0"};
                } else if (sub_type.equals("xmfy")) {
                    //暂时写个假的
                    guru_ids = new String[]{"11488b4bfe1e45e2a85666299d6bb720"};
                } else {
                    guru_ids = null;
                }
                articles = articleService.findByGuru(guru_ids);
                System.out.println(articles);
            }
            map.put("status", 200);
            map.put("head", head);
            map.put("albums", albums);
            map.put("article", articles);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "加载失败");
            return map;
        }
    }

    //文章详情
    @RequestMapping("articleDetail")
    public Map articleDetail(String id, String uid) {
        Map map = new HashMap();
        try {
            Article article = articleService.findById(id);
            map.put("status", 200);
            map.put("article", article);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "查询文章详情异常");
            return map;
        }
    }

}
