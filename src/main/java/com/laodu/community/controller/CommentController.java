package com.laodu.community.controller;

import com.laodu.community.dto.AjaxJson;
import com.laodu.community.dto.CommentCreateDTO;
import com.laodu.community.dto.CommentDTO;
import com.laodu.community.entity.User;
import com.laodu.community.exception.CustomizeErrorCode;
import com.laodu.community.exception.CustomizeException;
import com.laodu.community.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public AjaxJson saveComment(@RequestBody CommentCreateDTO commentCreateDTO,
                                HttpServletRequest req) {
       User user = (User) req.getSession().getAttribute("user");
       if (user == null) {
           throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
       }
       commentCreateDTO.setCommentator(user.getId());
        Object o = commentService.saveComment(commentCreateDTO);
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setMsg(o.toString());
        return ajaxJson;
    }

    @GetMapping("/comment/{id}")
    @ResponseBody
    public List<CommentDTO> getSecondryComments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.selectByQorCId(id, 1);
        return commentDTOS;
    }
}
