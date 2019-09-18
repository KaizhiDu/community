package com.laodu.community.service;

import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.Question;

import java.util.List;

public interface IQuestionService {

    public List<QuestionDTO> getQuestionDTO(int page, int size);

    public List<QuestionDTO> getQuestionDTOByUser(Long id, int page, int size);

    public QuestionDTO getQuestion(Long id);

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
