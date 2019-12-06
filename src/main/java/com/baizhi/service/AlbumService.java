package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  17:20
 */
public interface AlbumService {
    Map findAll(Integer page, Integer rows);

    List<Album> findByReleaseDate();

    Album findByID(String id);

    Map save(Album album);

    Map delete(String[] id);

    Map update(Album album);
}