package com.baizhi.backcontroller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.baizhi.util.FileURLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  19:27
 */
@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = bannerService.findByPage(page, rows);
        return map;
    }

    @RequestMapping("operation")
    public Map operation(Banner banner, String oper, String[] id) {
        Map map = new HashMap();
        if (oper.equals("add")) {
            banner.setUrl(null);
            map = bannerService.save(banner);
        } else if (oper.equals("del")) {
            /*Banner b = bannerService.findByID(banner.getId());
            if (b.getUrl() != null)
                deleteImg(b.getUrl());*/
            map = bannerService.delete(id);
        } else if (oper.equals("edit")) {
            banner.setUrl(null);
            map = bannerService.update(banner);
        }
        return map;
    }

    //上传图片
    @RequestMapping("upload")
    public Map uploadImg(HttpServletRequest request, MultipartFile url, String bannerId) {
        Map map = new HashMap();
        if (url.getOriginalFilename() == null || url.getOriginalFilename().length() == 0) {
            map.put("status", "400");
            map.put("msg", "文件为空");
            return map;
        }
        String URL = FileURLUtil.getURL(request, url, "/static/img/banner");
        //URL存入数据库
        Banner banner = new Banner();
        banner.setId(bannerId).setUrl(URL);
        map = bannerService.update(banner);
        return map;
    }

    //删除图片
    public void deleteImg(String url) {
        File file = new File(url);
        if (file != null)
            file.delete();
    }

    //EasyExcel导出excel
    @RequestMapping("outBanner")
    public void outBanner(HttpServletResponse response) {
        List<Banner> all = bannerService.findAll();
        ServletOutputStream os = null;
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");//设置类型
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("banner.xlsx", "UTF-8"));
            os = response.getOutputStream();
            EasyExcel.write(os, Banner.class).sheet().doWrite(all);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //导出轮播图信息
    /*@RequestMapping("outBanner")
    public void outBanner(HttpServletResponse response) {
        HSSFWorkbook sheets = new HSSFWorkbook();
        HSSFSheet sheet = sheets.createSheet("轮播图信息");
        List<Banner> banners = bannerService.findAll();
        HSSFRow row0 = sheet.createRow(0);
        String[] title = {"ID", "图片名称", "图片路径", "简介", "图片链接", "状态", "创建时间"};
        //字体居中样式
        HSSFCellStyle cellStyle = sheets.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //日期格式
        HSSFCellStyle cellStyleData = sheets.createCellStyle();
        HSSFDataFormat dataFormat = sheets.createDataFormat();
        cellStyleData.setAlignment(HorizontalAlignment.CENTER);
        cellStyleData.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));
        //标题
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row0.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(cellStyle);
        }
        //内容
        for (int i = 1; i < banners.size() + 1; i++) {
            HSSFRow row = sheet.createRow(i);
            Banner banner = banners.get(i - 1);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(banner.getId());
            cell0.setCellStyle(cellStyle);
            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(banner.getName());
            cell1.setCellStyle(cellStyle);
            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(banner.getUrl());
            cell2.setCellStyle(cellStyle);
            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(banner.getIntro());
            cell3.setCellStyle(cellStyle);
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(banner.getLink());
            cell4.setCellStyle(cellStyle);
            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(banner.getStatus());
            cell5.setCellStyle(cellStyle);
            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(banner.getCreate_date());
            cell6.setCellStyle(cellStyleData);
        }
        //导出
        ServletOutputStream os = null;
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("banner.xls", "UTF-8"));
            os = response.getOutputStream();
            sheets.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

}
