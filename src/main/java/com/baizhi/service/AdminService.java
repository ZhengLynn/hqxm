package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  16:57
 */
public interface AdminService {
    String login(HttpServletRequest request, Admin admin, String username, String code);

    //void securityCode(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
