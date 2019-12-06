package com.baizhi.backcontroller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  16:59
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //登录
    @RequestMapping("login")
    public String login(HttpServletRequest request, Admin admin, String code) {
        return adminService.login(request, admin, admin.getUsername(), code);
    }

    //安全退出
    @RequestMapping("safeOut")
    public void safeOut(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
    }

    //获取验证码
    @RequestMapping("securityCode")
    public void securityCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取生成的验证码
            String securityCode = SecurityCode.getSecurityCode();
            //将验证码存入session
            HttpSession session = request.getSession();
            session.setAttribute("securityCode", securityCode);
            //获取验证码图片
            BufferedImage image = SecurityImage.createImage(securityCode);
            //相应到客户端
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
