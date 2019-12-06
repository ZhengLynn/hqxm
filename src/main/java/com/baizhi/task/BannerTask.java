package com.baizhi.task;

import com.alibaba.excel.EasyExcel;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/3  18:56
 */
@Component
public class BannerTask {
    @Autowired
    private BannerService bannerService;

    @Scheduled(cron = "0 0 0 ? * MON")
    public void downBanner() {
        List<Banner> all = bannerService.findAll();
        EasyExcel.write("E:\\Dasktop\\Work\\Framework\\后期项目\\day7-EChartsGoEasy\\banner.xlsx", Banner.class)
                .sheet().doWrite(all);
    }
}
