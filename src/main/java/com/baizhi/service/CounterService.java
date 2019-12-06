package com.baizhi.service;

import com.baizhi.entity.Counter;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  16:05
 */
public interface CounterService {
    List<Counter> findByUidAndCId(String uid, String id);

    void save(Counter counter);

    void update(Counter counter);

    void delete(String id);
}
