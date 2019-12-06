package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/28  17:47
 */
public interface ArticleService {
    Map findAll(Integer page, Integer size);

    List<Article> findByReleaseDate();

    List<Article> findByGuru(String[] guru_ids);

    Article findById(String id);

    Map save(Article article);

    Map delete(String[] id);

    Map update(Article article);
}
