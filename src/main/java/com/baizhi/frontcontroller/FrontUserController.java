package com.baizhi.frontcontroller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import com.baizhi.util.MessageUtil;
import com.baizhi.util.SecurityCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: DarkSunrise
 * @date: 2019/12/4  10:34
 */
@RestController
@RequestMapping("fUser")
public class FrontUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    //登录
    @RequestMapping("login")
    public Map login(HttpSession session, String phone, String password) {
        Map map = new HashMap();
        try {
            User user = userService.findByPhone(phone);
            if (user == null) {
                map.put("status", -200);
                map.put("message", "手机号未注册");
                return map;
            }
            String mdPassword = MD5Utils.getPassword(password + user.getSalt());
            if (!user.getPassword().equals(mdPassword)) {
                map.put("status", -200);
                map.put("message", "密码错误");
                return map;
            }
            //修改最后登录时间
            user.setLast_date(new Date());
            userService.update(user);
            //存入session
            session.setAttribute("user", user);
            map.put("status", 200);
            map.put("user", user);
            return map;
        } catch (Exception e) {
            map.put("status", -200);
            map.put("message", "登录异常");
            e.printStackTrace();
            return map;
        }
    }

    //发送验证码
    @RequestMapping("sendCode")
    public Map sendCode(HttpSession session, String phone) {
        Map map = new HashMap();
        try {
            String code = SecurityCode.getSecurityCode();
            MessageUtil.sendMessage(phone, code);
            session.setAttribute("phone", phone);
            //将phone code存入Redis
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set(phone, code, 60 * 5, TimeUnit.SECONDS);
            map.put("status", 200);
            map.put("message", "验证码发送成功");
            return map;
        } catch (Exception e) {
            map.put("status", -200);
            map.put("message", "验证码发送异常");
            e.printStackTrace();
            return map;
        }
    }

    //注册
    @RequestMapping("register")
    public Map register(HttpSession session, String code) {
        Map map = new HashMap();
        try {
            String phone = (String) session.getAttribute("phone");
            String c = stringRedisTemplate.opsForValue().get(phone);
            if (!c.equals(code)) {
                map.put("status", -200);
                map.put("message", "验证码输入有误");
                return map;
            }
            //将用户信息存入数据库
            String id = UUID.randomUUID().toString().replace("-", "");
            User user = new User();
            user.setPhone(phone).setId(id).setStatus(1).setCreate_date(new Date());
            userService.save(user);
            map.put("status", 200);
            map.put("id", id);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "注册异常");
            return map;
        }
    }

    //补充个人信息
    @RequestMapping("replenish")
    public Map replenish(HttpSession session, User user) {
        Map map = new HashMap();
        try {
            //生成盐
            String salt = MD5Utils.getSalt();
            //加盐
            String password = MD5Utils.getPassword(user.getPassword() + salt);
            user.setPassword(password).setSalt(salt);
            map = userService.update(user);
            User u = userService.findById(user.getId());
            session.setAttribute("user", u);
            map.put("user", u);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "修改个人信息异常");
            return map;
        }
    }

    //金刚道友
    @RequestMapping("friend")
    public Map friend(String uid) {
        Map map = new HashMap();
        try {
            BoundHashOperations attention = redisTemplate.boundHashOps("attention");
            BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps(uid);
            //清空boundZSetOperations
            boundZSetOperations.removeRange(0, -1);
            //取出attention中的所有key
            Set<Object> keys = attention.keys();
            keys.forEach(key -> {
                //判断当前key下受否存在该用户
                Set<String> set = (Set<String>) attention.get(key);
                //若存在，则将该上师下所有用户存入一个ZSet
                if (set.contains(uid)) {
                    set.forEach(s -> {
                        boundZSetOperations.incrementScore(s, 1);
                    });
                }
            });
            //取ZSet中按分数倒叙取前5名
            Set<String> set = boundZSetOperations.reverseRange(0, -1);
            List<User> list = new ArrayList<>();
            for (String s : set) {
                if (list.size() == 5) break;
                if (!s.equals(uid)) {
                    User user = userService.findById(s);
                    list.add(user);
                }
            }
            map.put("status", 200);
            map.put("list", list);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -200);
            map.put("message", "寻找失败");
            return map;
        }
    }
}
