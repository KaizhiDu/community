package com.laodu.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping("/publish")
public class publishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest req, Model model) {

        if (question.getTitle()==null || question.getTitle().equals("")) {
            model.addAttribute("err", "you need a title");
            return "publish";
        }

        Cookie[] cookies = req.getCookies();
        User user = null;
        if (cookies.length != 0) {
            for (Cookie cookie : cookies ) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    QueryWrapper<User> wrapper = new QueryWrapper<>();
                    wrapper.eq("token", token);
                    user = userMapper.selectOne(wrapper);
                    break;
                }
            }
        }
        if (user == null) {
            model.addAttribute("err", "you need to login before publish");
        } else {
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setAvatarUrl(user.getAvatarUrl());
            questionMapper.insert(question);
            return "redirect:/";
        }
        return "publish";
    }
}
