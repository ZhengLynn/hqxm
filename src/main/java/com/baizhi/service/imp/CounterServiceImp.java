package com.baizhi.service.imp;

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
    public List<Counter> findByUidAndCId(String uid, String id) {
        return counterDao.findByUidAndCId(uid, id);
    }

    @Override
    public void save(Counter counter) {
        counterDao.insertSelective(counter);
    }

    @Override
    public void update(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
    }

    @Override
    public void delete(String id) {
        counterDao.deleteByPrimaryKey(id);
    }
}
