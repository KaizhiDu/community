package com.laodu.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.dto.PaginationDTO;
import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.helper.PaginationHelper;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.mapper.UserMapper;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String hello(HttpServletResponse res,
                        HttpServletRequest req,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1")int page) {
        int size = 5;
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
        List<QuestionDTO> questionDTO = questionService.getQuestionDTO(page, size);
        model.addAttribute("questionDTO", questionDTO);
        PaginationHelper paginationHelper = new PaginationHelper();
        int total = questionMapper.selectCount(null);
        PaginationDTO pagination = paginationHelper.getCurrentPageList(page, total, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }

}
