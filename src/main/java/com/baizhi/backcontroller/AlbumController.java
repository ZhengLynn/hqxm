package com.baizhi.backcontroller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.util.FileURLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  17:43
 */
@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        Map map = albumService.findAll(page, rows);
        return map;
    }

    @RequestMapping("operation")
    public Map operation(String oper, String[] id, Album album) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            album.setCover(null);
            map = albumService.save(album);
        } else if (oper.equals("del")) {
            map = albumService.delete(id);
        } else if (oper.equals("edit")) {
            album.setCover(null);
            map = albumService.update(album);
        }
        return map;
    }

    //上传图片
    @RequestMapping("upload")
    public Map uploadImg(HttpServletRequest request, MultipartFile cover, String albumId) {
        Map map = new HashMap();
        if (cover.getOriginalFilename().length() == 0) {
            map.put("status", "400");
            map.put("msg", "文件为空");
            return map;
        }
        String URL = FileURLUtil.getURL(request, cover, "/static/img/album/");
        //URL存入数据库
        Album album = new Album();
        album.setId(albumId).setCover(URL);
        albumService.update(album);

        map.put("status", "200");
        map.put("msg", "上传成功");
        return map;
    }

}
