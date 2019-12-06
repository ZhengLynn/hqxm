package com.baizhi.service.imp;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  17:34
 */
@Service
@Transactional
public class ChapterServiceImp implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows, String album_id) {
        Map map = new HashMap();
        List<Chapter> chapters = chapterDao.selectByRowBounds(new Chapter().setAlbum_id(album_id), new RowBounds((page - 1) * rows, rows));
        int records = chapterDao.selectCount(new Chapter());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", chapters);
        map.put("records", records);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Chapter findByID(String id) {
        return chapterDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Chapter> findByAlbumId(String albumId) {
        return chapterDao.findByAlbumId(albumId);
    }

    @Override
    @LogAnnotation("添加章节")
    public Map save(Chapter chapter) {

        System.out.println(chapter);

        Map map = new HashMap();
        String s = UUID.randomUUID().toString().replace("-", "");
        chapter.setId(s);
        if (chapter.getUpload_date() == null || chapter.getUpload_date().equals("")) chapter.setUpload_date(new Date());
        chapterDao.insertSelective(chapter);
        map.put("chapterId", s);
        map.put("msg", "添加成功");
        return map;
    }

    @Override
    @LogAnnotation("删除专辑")
    public Map delete(String[] id) {
        Map map = new HashMap();
        List<String> list = Arrays.asList(id);
        chapterDao.deleteByIdList(list);
        map.put("status", 200);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    @LogAnnotation("修改专辑信息")
    public Map update(Chapter chapter) {
        Map map = new HashMap();
        chapterDao.updateByPrimaryKeySelective(chapter);
        map.put("status", 200);
        map.put("msg", "修改成功");
        return map;
    }
}
