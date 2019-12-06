package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  17:19
 */
public interface ChapterDao extends Mapper<Chapter>, DeleteByIdListMapper<Chapter, String> {
    List<Chapter> findByAlbumId(String albumId);
}
