package com.baizhi.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  11:58
 */
public class FileURLUtil {
    public static String getURL(HttpServletRequest request, MultipartFile file, String dir) {
        String realPath = request.getSession().getServletContext().getRealPath(dir);
        File f = new File(realPath);
        if (!f.exists()) f.mkdirs();
        //防止重名
        String fileName = new Date().getTime() + "_" + file.getOriginalFilename();
        //上传文件
        try {
            file.transferTo(new File(realPath, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String http = request.getScheme();
        String[] split = new String[0];
        try {
            split = InetAddress.getLocalHost().toString().split("/");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String localhost = split[split.length - 1];
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        //拼接
        String url = http + "://" + localhost + ":" + serverPort + contextPath + dir + fileName;
        return url;
    }
}
