package com.laodu.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.mapper.UserMapper;
import com.laodu.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionDTO> getQuestionDTO(int currentPage, int size) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        List<QuestionDTO> questionDTOS = getQuestionDTOList(currentPage, size, wrapper);
        return questionDTOS;
    }

    @Override
    public List<QuestionDTO> getQuestionDTOByUser(int id, int currentPage, int size) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator", id);
        List<QuestionDTO> questionDTOS = getQuestionDTOList(currentPage, size, wrapper);
        return questionDTOS;
    }

    @Override
    public QuestionDTO getQuestion(int id) {
        Question question = questionMapper.selectById(id);
        User user = userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        questionDTO.setCreateDate(simpleDateFormat.format(question.getGmtCreate()));
        return questionDTO;
    }

    public List<QuestionDTO> getQuestionDTOList(int currentPage, int size, QueryWrapper<Question> wrapper) {
        IPage<Question> page = new Page<>(currentPage, size);
        IPage<Question> questionIPage = questionMapper.selectPage(page, wrapper);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question q : questionIPage.getRecords()) {
            User user = userMapper.selectById(q.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            BeanUtils.copyProperties(q, questionDTO);
            questionDTO.setUser(user);
            questionDTO.setCreateDate(simpleDateFormat.format(q.getGmtCreate()));
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
