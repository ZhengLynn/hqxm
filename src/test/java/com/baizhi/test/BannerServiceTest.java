package com.baizhi.test;

import com.baizhi.CmfzApplication;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  19:40
 */
@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class BannerServiceTest {
    @Autowired
    private BannerService bannerService;

    /*@Test
    public void test1(){
        Map all = bannerService.findAll(0, 3);
        List<Banner> rows = (List<Banner>) all.get("rows");
        rows.forEach(banner -> System.out.println(banner));
    }*/
    /*@Test
    public void test2(){
        Integer count = bannerService.count();
        System.out.println(count);
    }*/
    @Test
    public void test3() {
        Banner banner = new Banner();
        //banner.setId("3");
        bannerService.save(banner);
        System.out.println(banner.getId());
    }

    /*@Test
    public void test4(){
        bannerService.delete("3");
    }*/
    @Test
    public void test5() {
        Banner banner = new Banner();
        banner.setId("2").setName("123");
        bannerService.update(banner);
    }
}
