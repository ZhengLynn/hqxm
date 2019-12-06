package com.baizhi.service.imp;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/11/27  17:21
 */
@Service
@Transactional
public class AlbumServiceImp implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Album> albums = albumDao.selectByRowBounds(new Album(), new RowBounds((page - 1) * rows, rows));
        int records = albumDao.selectCount(new Album());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", albums);
        map.put("total", total);
        map.put("page", page);
        map.put("records", records);
        return map;
    }

    @Override
    public List<Album> findByReleaseDate() {
        return albumDao.findByReleaseDate();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album findByID(String id) {
        return albumDao.selectByPrimaryKey(id);
    }

    @Override
    @LogAnnotation("添加专辑")
    public Map save(Album album) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString().replace("-", "");
        album.setId(s);
        if (album.getRelease_date() == null) album.setRelease_date(new Date());
        if (album.getUpload_date() == null) album.setUpload_date(new Date());
        albumDao.insertSelective(album);
        map.put("albumId", s);
        map.put("msg", "添加成功");
        return map;
    }

    @Override
    @LogAnnotation("删除专辑")
    public Map delete(String[] id) {
        Map map = new HashMap();
        List<String> list = Arrays.asList(id);
        albumDao.deleteByIdList(list);
        map.put("status", 200);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    @LogAnnotation("修改专辑信息")
    public Map update(Album album) {
        Map map = new HashMap();
        albumDao.updateByPrimaryKeySelective(album);
        map.put("status", 200);
        map.put("msg", "更改成功");
        return map;
    }
}
