package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/12/1  9:22
 */
public interface UserService {
    Map findAll(Integer page, Integer rows);

    User findById(String id);

    User findByPhone(String phone);

    Map statTime();

    List statCity();

    Map save(User user);

    Map delete(String[] id);

    Map update(User user);
}
