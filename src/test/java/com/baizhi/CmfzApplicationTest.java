package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  11:33
 */
@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class CmfzApplicationTest {
    @Autowired
    private AdminDao adminDao;

    @Test
    public void test1() {
        List<Admin> admins = adminDao.selectAll();
        admins.forEach(admin -> System.out.println(admin));
    }
}
