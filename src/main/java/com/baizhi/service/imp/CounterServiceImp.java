package com.baizhi.service.imp;

import com.baizhi.annotation.AddCacheAnnotation;
import com.baizhi.annotation.DelCacheAnnotation;
import com.baizhi.dao.CounterDao;
import com.baizhi.entity.Counter;
import com.baizhi.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  16:06
 */
@Service
@Transactional
public class CounterServiceImp implements CounterService {
    @Autowired
    private CounterDao counterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public List<Counter> findByUidAndCId(String uid, String id) {
        return counterDao.findByUidAndCId(uid, id);
    }

    @Override
    @DelCacheAnnotation
    public void save(Counter counter) {
        counterDao.insertSelective(counter);
    }

    @Override
    @DelCacheAnnotation
    public void update(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
    }

    @Override
    @DelCacheAnnotation
    public void delete(String id) {
        counterDao.deleteByPrimaryKey(id);
    }
}
