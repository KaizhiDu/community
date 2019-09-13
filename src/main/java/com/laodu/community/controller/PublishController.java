package com.laodu.community.controller;

import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish/{id}")
    public String updatePublish(@PathVariable(name = "id") int id,
                                Model model) {
        Question question = questionMapper.selectById(id);
        model.addAttribute("question", question);
        model.addAttribute("id", id);
        return "publish";
    }

    @GetMapping("/publish")
    public String createPublish(Model model) {
        // 前台显示为空
        Question question1 = new Question();
        model.addAttribute("question", question1);
        model.addAttribute("id", -1);

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest req, Model model) {
        if (question.getTitle() == null || question.getTitle().equals("")) {
            model.addAttribute("err", "you need a title");
            return "publish";
        }

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("err", "you need to login before publish");
        } else {
            // update
            if (question.getId() != -1) {
                question.setGmtModified(System.currentTimeMillis());
                question.setCreator(user.getId());
                questionMapper.updateById(question);
            }
            // create
            else {
                question.setId(0L);
                question.setCreator(user.getId());
                question.setGmtCreate(System.currentTimeMillis());
                question.setGmtModified(question.getGmtCreate());
                questionMapper.insert(question);
            }
            return "redirect:/";
        }

        return "publish";
    }
}
