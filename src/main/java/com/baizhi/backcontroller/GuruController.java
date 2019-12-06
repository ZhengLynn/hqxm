package com.baizhi.backcontroller;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import com.baizhi.util.FileURLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/29  14:28
 */
@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;

    @RequestMapping("findAll")
    public List<Guru> findAll() {
        List<Guru> gurus = guruService.findAll();
        return gurus;
    }

    @RequestMapping("findByPage")
    public Map findByPage(Integer page, Integer rows) {
        Map map = guruService.findByPage(page, rows);
        return map;
    }

    @RequestMapping("operation")
    public Map operation(String oper, Guru guru, String[] id) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            map = guruService.save(guru);
        } else if (oper.equals("edit")) {
            guru.setPhoto(null);
            map = guruService.update(guru);
        } else if (oper.equals("del")) {
            map = guruService.delete(id);
        }
        return map;
    }

    @RequestMapping("upload")
    public Map upload(HttpServletRequest request, MultipartFile photo, String guruId) {
        Map map = new HashMap();
        if (photo.getOriginalFilename() == null || photo.getOriginalFilename().length() == 0) {
            map.put("status", 500);
            map.put("msg", "文件为空");
        }
        String dir = "/staticc/img/guru/";
        String url = FileURLUtil.getURL(request, photo, dir);
        //存入数据库
        Guru guru = new Guru();
        guru.setId(guruId).setPhoto(url);
        map = guruService.update(guru);
        return map;
    }
}
