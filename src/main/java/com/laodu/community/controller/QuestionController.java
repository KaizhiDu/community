package com.laodu.community.controller;

import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String getQuestion(@PathVariable(name = "id") int id,
                              Model model) {

        QuestionDTO question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question";
    }
}
