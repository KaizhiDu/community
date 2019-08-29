package com.laodu.community.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String hello(HttpServletResponse res, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies.length != 0) {
            for (Cookie cookie : cookies ) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    QueryWrapper<User> wrapper = new QueryWrapper<>();
                    wrapper.eq("token", token);
                    User user = userMapper.selectOne(wrapper);
                    if (user != null) {
                        req.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return "index";
    }

}
