package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  17:20
 */
public interface ChapterService {
    Map findAll(Integer page, Integer rows, String album_id);

    Chapter findByID(String id);

    List<Chapter> findByAlbumId(String albumId);

    Map save(Chapter chapter);

    Map delete(String[] id);

    Map update(Chapter chapter);
}
