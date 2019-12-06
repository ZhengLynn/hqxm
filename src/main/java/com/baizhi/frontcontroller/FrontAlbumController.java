package com.baizhi.frontcontroller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  14:59
 */
@RestController
@RequestMapping("fAlbum")
public class FrontAlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("albumDetail")
    public Map albumDetail(String id, String uid) {
        Map map = new HashMap();
        try {
            Album album = albumService.findByID(id);
            List<Chapter> chapters = chapterService.findByAlbumId(id);
            map.put("status", 200);
            map.put("album", album);
            map.put("list", chapters);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "加载失败");
            return map;
        }
    }
}
