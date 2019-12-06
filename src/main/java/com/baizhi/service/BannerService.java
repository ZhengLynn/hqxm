package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  19:22
 */
public interface BannerService {
    Map findByPage(Integer page, Integer rows);

    List<Banner> findAll();

    Banner findByID(String id);

    List<Banner> findByRand();

    Map save(Banner banner);

    Map delete(String[] id);

    Map update(Banner banner);
}
