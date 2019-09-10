package com.laodu.community.service;

import com.laodu.community.dto.QuestionDTO;

import java.util.List;

public interface IQuestionService {

    public List<QuestionDTO> getQuestionDTO(int page, int size);

    public List<QuestionDTO> getQuestionDTOByUser(int id, int page, int size);

    public QuestionDTO getQuestion(int id);
}
