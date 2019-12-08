package com.baizhi.service.imp;

import com.baizhi.annotation.AddCacheAnnotation;
import com.baizhi.annotation.DelCacheAnnotation;
import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/11/28  17:50
 */
@Service
@Transactional
public class ArticleServiceImp implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Article> articles = articleDao.selectByRowBounds(new Article(), new RowBounds((page - 1) * rows, rows));
        int records = articleDao.selectCount(new Article());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", articles);
        map.put("page", page);
        map.put("total", total);
        map.put("records", records);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public List<Article> findByReleaseDate() {
        List<Article> articles = articleDao.findByReleaseDate();
        articles.forEach(article -> {
            Guru guru = guruDao.selectByPrimaryKey(article.getGuru_id());
            article.setGuru(guru);
        });
        return articles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public List<Article> findByGuru(String[] guru_ids) {
        List<Article> list = new ArrayList<>();
        for (String guru_id : guru_ids) {
            Guru guru = guruDao.selectByPrimaryKey(guru_id);
            List<Article> articles = articleDao.findByGuru(guru_id);
            articles.forEach(article -> article.setGuru(guru));
            list.addAll(articles);
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public Article findById(String id) {
        return articleDao.selectByPrimaryKey(id);
    }

    @Override
    @LogAnnotation("添加文章")
    @DelCacheAnnotation
    public Map save(Article article) {
        Map map = new HashMap();
        if (article.getRelease_date() == null) {
            article.setRelease_date(new Date());
        }
        articleDao.insertSelective(article);
        map.put("status", 200);
        map.put("msg", "添加成功");
        return map;
    }

    @Override
    @LogAnnotation("删除文章")
    @DelCacheAnnotation
    public Map delete(String[] id) {
        List<String> list = Arrays.asList(id);
        Map map = new HashMap();
        articleDao.deleteByIdList(list);
        map.put("status", 200);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    @LogAnnotation("修改文章信息")
    @DelCacheAnnotation
    public Map update(Article article) {
        Map map = new HashMap();
        articleDao.updateByPrimaryKeySelective(article);
        map.put("status", 200);
        map.put("msg", "修改成功");
        return map;
    }
}
