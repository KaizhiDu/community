package com.laodu.community.service;

import com.laodu.community.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {

    public List<QuestionDTO> getQuestionDTO(int page, int size);

    public List<QuestionDTO> getQuestionDTOByUser(int id, int page, int size);
}
