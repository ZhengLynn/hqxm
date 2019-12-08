package com.baizhi.service.imp;

import com.baizhi.annotation.AddCacheAnnotation;
import com.baizhi.annotation.ChartAnnotation;
import com.baizhi.annotation.DelCacheAnnotation;
import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/12/1  9:25
 */
@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<User> users = userDao.selectByRowBounds(new User(), new RowBounds((page - 1) * rows, rows));
        Integer records = userDao.selectCount(new User());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", users);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCacheAnnotation
    public User findById(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    @AddCacheAnnotation
    public User findByPhone(String phone) {
        User user = userDao.selectOne(new User().setPhone(phone));
        return user;
    }

    @Override
    public Map statTime() {
        List man = new ArrayList();
        List woman = new ArrayList();
        Integer[] day = {1, 7, 30, 365};
        for (int i = 0; i < 4; i++) {
            Integer count = userDao.statTime("man", day[i]);
            man.add(count);
        }
        for (int i = 0; i < 4; i++) {
            Integer count = userDao.statTime("woman", day[i]);
            woman.add(count);
        }
        Map map = new HashMap();
        map.put("man", man);
        map.put("woman", woman);
        return map;
    }

    @Override
    public List statCity() {
        return userDao.statCity();
    }

    @Override
    @LogAnnotation("添加用户")
    @ChartAnnotation
    @DelCacheAnnotation
    public Map save(User user) {
        Map map = new HashMap();
        if (user.getCreate_date() == null) user.setCreate_date(new Date());
        if (user.getLast_date() == null) user.setLast_date(new Date());
        userDao.insertSelective(user);
        map.put("userId", user.getId());
        map.put("status", 200);
        map.put("message", "添加成功");
        return map;
    }

    @Override
    @LogAnnotation("删除用户")
    @ChartAnnotation
    @DelCacheAnnotation
    public Map delete(String[] id) {
        Map map = new HashMap();
        userDao.deleteByIdList(Arrays.asList(id));
        map.put("status", 200);
        map.put("message", "删除成功");
        return map;
    }

    @Override
    @LogAnnotation("修改用户信息")
    @ChartAnnotation
    @DelCacheAnnotation
    public Map update(User user) {
        Map map = new HashMap();
        userDao.updateByPrimaryKeySelective(user);
        map.put("status", 200);
        map.put("message", "修改成功");
        return map;
    }
}
