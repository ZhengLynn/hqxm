package com.baizhi.dao;


import com.baizhi.entity.Album;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  17:19
 */
public interface AlbumDao extends Mapper<Album>, DeleteByIdListMapper<Album, String> {
    List<Album> findByReleaseDate();
}
