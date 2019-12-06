package com.baizhi.service.imp;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  16:57
 */
@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private AdminDao adminDao;

    //登录验证
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String login(HttpServletRequest request, Admin admin, String username, String code) {
        Admin a = adminDao.selectOne(new Admin(null, username, null));
        String securityCode = (String) request.getSession().getAttribute("securityCode");
        if (!code.equals(securityCode)) {
            return "验证码错误";
        } else if (a == null) {
            return "用户名不存在";
        } else if (!a.getPassword().equals(admin.getPassword())) {
            return "密码错误";
        } else {
            request.getSession().setAttribute("admin", a);
            return "登陆成功";
        }
    }
}
