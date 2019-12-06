package com.baizhi.service.imp;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  19:23
 */
@Service
@Transactional
public class BannerServiceImp implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        if (page == null) page = 0;
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page - 1) * rows, rows));
        int records = bannerDao.selectCount(new Banner());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", banners);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    @Override
    public List<Banner> findAll() {
        return bannerDao.selectAll();
    }

    @Override
    public Banner findByID(String id) {
        return bannerDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Banner> findByRand() {
        return bannerDao.findByRand();
    }

    @Override
    @LogAnnotation("添加轮播图")
    public Map save(Banner banner) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString().replace("-", "");
        banner.setId(s);
        if (banner.getCreate_date() == null) banner.setCreate_date(new Date());
        bannerDao.insert(banner);
        map.put("bannerId", s);
        map.put("status", 200);
        return map;
    }

    @Override
    @LogAnnotation("删除轮播图")
    public Map delete(String[] id) {
        Map map = new HashMap();
        List<String> list = Arrays.asList(id);
        bannerDao.deleteByIdList(list);
        map.put("status", 200);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    @LogAnnotation("修改轮播图信息")
    public Map update(Banner banner) {
        Map map = new HashMap();
        bannerDao.updateByPrimaryKeySelective(banner);
        map.put("status", 200);
        map.put("msg", "更新成功");
        return map;
    }
}
