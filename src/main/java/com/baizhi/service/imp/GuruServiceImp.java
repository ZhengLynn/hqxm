package com.baizhi.service.imp;

import com.baizhi.annotation.AddCacheAnnotation;
import com.baizhi.annotation.DelCacheAnnotation;
import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/11/29  14:20
 */
@Service
@Transactional
public class GuruServiceImp implements GuruService {
    @Autowired
    private GuruDao guruDao;

    @Override
    @AddCacheAnnotation
    public Map findByPage(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Guru> gurus = guruDao.selectByRowBounds(new Guru(), new RowBounds((page - 1) * rows, rows));
        Integer records = guruDao.selectCount(new Guru());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", gurus);
        map.put("page", page);
        map.put("total", total);
        map.put("records", records);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public List<Guru> findAll() {
        return guruDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public Guru findById(String id) {
        return guruDao.selectByPrimaryKey(id);
    }

    @Override
    @LogAnnotation("添加上师")
    @DelCacheAnnotation
    public Map save(Guru guru) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString().replace("-", "");
        guru.setId(s);
        guruDao.insertSelective(guru);
        map.put("guruId", s);
        map.put("status", 200);
        map.put("msg", "添加成功");
        return map;
    }

    @Override
    @LogAnnotation("删除上师")
    @DelCacheAnnotation
    public Map delete(String[] id) {
        Map map = new HashMap();
        guruDao.deleteByIdList(Arrays.asList(id));
        map.put("status", 200);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    @LogAnnotation("修改上师信息")
    @DelCacheAnnotation
    public Map update(Guru guru) {
        Map map = new HashMap();
        guruDao.updateByPrimaryKeySelective(guru);
        map.put("status", 200);
        map.put("msg", "修改成功");
        return map;
    }
}
