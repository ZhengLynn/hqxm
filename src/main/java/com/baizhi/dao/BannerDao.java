package com.baizhi.dao;

import com.baizhi.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  19:18
 */
public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner, String> {
    List<Banner> findByRand();
}
