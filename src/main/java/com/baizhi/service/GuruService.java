package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/29  14:19
 */
public interface GuruService {
    Map findByPage(Integer page, Integer rows);

    List<Guru> findAll();

    Guru findById(String id);

    Map save(Guru guru);

    Map delete(String[] id);

    Map update(Guru guru);
}
