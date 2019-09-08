package com.laodu.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.dto.PaginationDTO;
import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.helper.PaginationHelper;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.mapper.UserMapper;
import com.laodu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          Model model,
                          HttpServletRequest req) {
        int size = 5;
        User user = null;
        Cookie[] cookies = req.getCookies();
        if (cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    QueryWrapper<User> wrapper = new QueryWrapper<>();
                    wrapper.eq("token", token);
                    user = userMapper.selectOne(wrapper);
                    if (user != null) {
                        req.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        if (user == null) return "redirect:/";

        switch (action) {
            case "questions": {
                model.addAttribute("section", "questions");
                model.addAttribute("sectionName", "My Questions");
            }
            break;
            case "replies": {
                model.addAttribute("section", "replies");
                model.addAttribute("sectionName", "New Responses");
            }
            break;
        }

        List<QuestionDTO> questionDTOByUser = questionService.getQuestionDTOByUser(user.getId(), page, size);
        model.addAttribute("questions", questionDTOByUser);
        PaginationHelper paginationHelper = new PaginationHelper();
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator", user.getId());
        Integer total = questionMapper.selectCount(wrapper);
        PaginationDTO pagination = paginationHelper.getCurrentPageList(page, total, size);
        model.addAttribute("pagination", pagination);
        return "profile";
    }
}
