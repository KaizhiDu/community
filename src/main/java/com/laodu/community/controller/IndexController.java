package com.laodu.community.controller;

import com.laodu.community.dto.PaginationDTO;
import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.helper.PaginationHelper;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1")int page) {
        int size = 5;
        List<QuestionDTO> questionDTO = questionService.getQuestionDTO(page, size);
        model.addAttribute("questionDTO", questionDTO);
        PaginationHelper paginationHelper = new PaginationHelper();
        int total = questionMapper.selectCount(null);
        PaginationDTO pagination = paginationHelper.getCurrentPageList(page, total, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }

}
