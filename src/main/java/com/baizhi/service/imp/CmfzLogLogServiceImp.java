package com.baizhi.service.imp;

import com.baizhi.dao.CmfzLogDao;
import com.baizhi.entity.CmfzLog;
import com.baizhi.service.CmfzLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: DarkSunrise
 * @date: 2019/11/28  17:00
 */
@Service
@Transactional
public class CmfzLogLogServiceImp implements CmfzLogService {
    @Autowired
    private CmfzLogDao cmfzLogDao;

    @Override
    public Map save(CmfzLog cmfzLog) {
        Map map = new HashMap();
        cmfzLogDao.insertSelective(cmfzLog);
        map.put("status", 200);
        map.put("msg", "添加成功");
        return map;
    }
}
