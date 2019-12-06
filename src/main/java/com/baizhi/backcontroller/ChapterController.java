package com.baizhi.backcontroller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.FileURLUtil;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  20:52
 */
@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows, String album_id) {
        Map all = chapterService.findAll(page, rows, album_id);
        return all;
    }

    @RequestMapping("operation")
    public Map operation(String oper, String[] id, Chapter chapter) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            chapter.setPath(null);
            map = chapterService.save(chapter);
        } else if (oper.equals("del")) {
            map = chapterService.delete(id);
        } else if (oper.equals("edit")) {
            chapter.setPath(null);
            map = chapterService.update(chapter);
        }
        return map;
    }

    @RequestMapping("upload")
    public Map upload(MultipartFile path, HttpServletRequest request, String chapterId) {
        Map map = new HashMap();
        if (path.getOriginalFilename().length() == 0) {
            map.put("status", "400");
            map.put("msg", "文件为空");
            return map;
        }
        String url = FileURLUtil.getURL(request, path, "/static/audio/chapter/");
        //获取文件名
        String[] split = url.split("/");
        String s = split[split.length - 1];
        String realPath = request.getSession().getServletContext().getRealPath("/static/audio/chapter/" + s);
        File file = new File(realPath);
        long length = file.length();
        String size = String.format("%.2f", (double) length / 1024 / 1024) + "MB";
        MP3File read = null;
        try {
            read = (MP3File) AudioFileIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MP3AudioHeader mp3AudioHeader = read.getMP3AudioHeader();
        //获取当前音频有多少秒
        int trackLength = mp3AudioHeader.getTrackLength();
        String min = trackLength / 60 + "分";
        String sec = trackLength % 60 + "秒";
        //存入数据库
        Chapter chapter = new Chapter();
        chapter.setId(chapterId).setPath(s).setSize(size).setTime(min + sec);
        chapterService.update(chapter);

        map.put("status", "200");
        map.put("msg", "上传成功");
        return map;
    }

    //下载
    @RequestMapping("down")
    public void down(HttpServletRequest request, HttpServletResponse response, String url) {
        String realPath = request.getSession().getServletContext().getRealPath("/static/audio/chapter/" + url);
        File file = new File(realPath);
        try {
            FileInputStream is = new FileInputStream(file);
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(url, "UTF-8"));
            ServletOutputStream os = response.getOutputStream();
            IOUtils.copy(is, os);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
