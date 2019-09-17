package com.laodu.community.service;

import com.laodu.community.dto.CommentCreateDTO;
import com.laodu.community.dto.CommentDTO;

import java.util.List;

public interface ICommentService {
    public Object saveComment(CommentCreateDTO commentCreateDTO);

    public List<CommentDTO> selectByQorCId(Long id, int type);
}
