package com.baizhi.dao;

import com.baizhi.entity.Guru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author: DarkSunrise
 * @date: 2019/11/29  14:18
 */
public interface GuruDao extends Mapper<Guru>, DeleteByIdListMapper<Guru, String> {

}
