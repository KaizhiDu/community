package com.laodu.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.dto.CommentCreateDTO;
import com.laodu.community.dto.CommentDTO;
import com.laodu.community.entity.Comment;
import com.laodu.community.entity.Question;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.CommentMapper;
import com.laodu.community.mapper.QuestionMapper;
import com.laodu.community.mapper.UserMapper;
import com.laodu.community.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Object saveComment(CommentCreateDTO commentCreateDTO) {


        //如果是type是1，则是评论的回复
        if (commentCreateDTO.getType() == 1) {
            Comment comment = commentMapper.selectById(commentCreateDTO.getParentId());
            comment.setCommentCount(comment.getCommentCount() + 1);
            commentMapper.updateById(comment);
        }

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(commentCreateDTO.getCommentator());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentCount(0l);

        int insert = 0;

        if (commentCreateDTO.getType() == 1) {
            // 回复评论
            insert = commentMapper.insert(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            //comment + 1
            question.setCommentCount(question.getCommentCount()+1);
            questionMapper.updateById(question);
            insert = commentMapper.insert(comment);
        }
        if (insert == 1) return "comment success";
        else return "comment fail";
    }

    @Override
    public List<CommentDTO> selectByQorCId(Long id, int type) {

        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        wrapper.eq("type",type);
        wrapper.orderByDesc("gmt_create");
        List<Comment> comments = commentMapper.selectList(wrapper);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment c : comments) {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(c, commentDTO);
            User user = userMapper.selectById(c.getCommentator());
            commentDTO.setUser(user);
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }
}
