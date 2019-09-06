package com.laodu.community.service.impl;

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
import java.util.Calendar;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionDTO> getQuestionDTO() {
        List<Question> questions = questionMapper.selectList(null);
        List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
        for (Question q : questions) {
            User user = userMapper.selectById(q.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            BeanUtils.copyProperties(q, questionDTO);
            questionDTO.setUser(user);
            questionDTO.setCreateDate(simpleDateFormat.format(q.getGmtCreate()));
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
}
