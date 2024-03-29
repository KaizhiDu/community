package com.laodu.community.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = req.getCookies();
        if (cookies!= null && cookies.length != 0) {
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
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
