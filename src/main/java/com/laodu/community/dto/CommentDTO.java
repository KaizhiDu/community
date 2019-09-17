package com.laodu.community.dto;

import com.laodu.community.entity.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private int type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private int likeCount;
    private String content;
    private Long commentCount;
    private User user;
}
