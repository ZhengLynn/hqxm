package com.baizhi.dao;

import com.baizhi.entity.User;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/1  9:21
 */
public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User, String> {
    Integer statTime(String sex, Integer day);

    List<User> statCity();
}
