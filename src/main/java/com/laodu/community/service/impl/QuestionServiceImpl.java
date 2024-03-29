package com.laodu.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.exception.CustomizeErrorCode;
import com.laodu.community.exception.CustomizeException;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.mapper.UserMapper;
import com.laodu.community.service.IQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionDTO> getQuestionDTO(int currentPage, int size, QueryWrapper<Question> wrapper) {
        wrapper.orderByDesc("gmt_create");
        List<QuestionDTO> questionDTOS = getQuestionDTOList(currentPage, size, wrapper);
        return questionDTOS;
    }

    @Override
    public List<QuestionDTO> getQuestionDTOByUser(Long id, int currentPage, int size) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator", id);
        List<QuestionDTO> questionDTOS = getQuestionDTOList(currentPage, size, wrapper);
        return questionDTOS;
    }

    @Override
    public QuestionDTO getQuestion(Long id) {
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        question.setViewCount(question.getViewCount()+1);
        questionMapper.updateById(question);
        User user = userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        questionDTO.setCreateDate(simpleDateFormat.format(question.getGmtCreate()));
        return questionDTO;
    }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        String tag = questionDTO.getTag();
        QueryWrapper<Question> wrapper = new QueryWrapper<>();

        String[] tags = tag.split(",");
        for (String s : tags) {
            wrapper.like("tag", s);
            wrapper.or();
        }
        List<Question> questions = questionMapper.selectList(wrapper);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question q : questions) {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO1);
            User user = userMapper.selectById(q.getCreator());
            questionDTO1.setUser(user);
            questionDTOs.add(questionDTO1);
        }
        return questionDTOs;
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
