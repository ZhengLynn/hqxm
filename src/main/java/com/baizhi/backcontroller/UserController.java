package com.baizhi.backcontroller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
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
 * @date: 2019/12/1  9:32
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        return userService.findAll(page, rows);
    }

    @RequestMapping("operation")
    public Map operation(String oper, String[] id, User user) {
        Map map = new HashMap();
        if (oper.equals("edit")) {
            user.setPhoto(null);
            user.setCreate_date(null);
            user.setLast_date(null);
            map = userService.update(user);
        }
        return map;
    }

    @RequestMapping("upload")
    public Map upload(HttpServletRequest request, MultipartFile photo, String userId) {
        Map map = new HashMap();
        if (photo.getOriginalFilename() == null || photo.getOriginalFilename().length() == 0) {
            map.put("status", 500);
            map.put("msg", "图片为空");
            return map;
        }
        String url = FileURLUtil.getURL(request, photo, "/static/img/user/");
        //存入数据库
        User user = new User();
        user.setId(userId).setPhoto(url);
        map = userService.update(user);
        return map;
    }

    //统计注册时间信息
    @RequestMapping("statTime")
    public Map statTime() {
        Map map = userService.statTime();
        return map;
    }

    //统计用户地区信息
    @RequestMapping("statCity")
    public List statCity() {
        return userService.statCity();
    }
}
