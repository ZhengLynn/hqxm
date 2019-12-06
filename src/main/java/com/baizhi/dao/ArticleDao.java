package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/11/28  17:46
 */
public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article, String> {
    List<Article> findByReleaseDate();

    List<Article> findByGuru(@Param("guru_id") String guru_id);
}
