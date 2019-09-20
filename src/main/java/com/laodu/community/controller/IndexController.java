package com.laodu.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.dto.PaginationDTO;
import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.Question;
import com.laodu.community.helper.PaginationHelper;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private IQuestionService IQuestionService;

    @Value("${page.size}")
    private int size;

    @RequestMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1")int page,
                        @RequestParam(name = "search", defaultValue = "") String search) {
        // 添加搜索
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (!search.equals("") || search != null) {
            String[] searches = search.split(" ");
            for (String s: searches) {
                wrapper.like("title",s);
                wrapper.or();
            }
        }
        List<QuestionDTO> questionDTO = IQuestionService.getQuestionDTO(page, size, wrapper);
        model.addAttribute("questionDTO", questionDTO);
        PaginationHelper paginationHelper = new PaginationHelper();
        int total = questionMapper.selectCount(wrapper);
        PaginationDTO pagination = paginationHelper.getCurrentPageList(page, total, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        return "index";
    }

}
