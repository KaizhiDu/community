package com.laodu.community.controller;

import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.User;
import com.laodu.community.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    private IQuestionService IQuestionService;

    @GetMapping("/question/{id}")
    public String getQuestion(@PathVariable(name = "id") int id,
                              Model model,
                              HttpServletRequest req) {

        QuestionDTO question = IQuestionService.getQuestion(id);
        model.addAttribute("question", question);
        User user = (User) req.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "question";
    }
}
