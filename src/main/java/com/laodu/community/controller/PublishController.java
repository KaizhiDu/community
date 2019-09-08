package com.laodu.community.controller;

import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

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

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("err", "you need to login before publish");
        } else {
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
            return "redirect:/";
        }
        return "publish";
    }
}
