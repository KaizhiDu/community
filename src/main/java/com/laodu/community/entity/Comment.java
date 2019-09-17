package com.laodu.community.entity;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long parentId;
    private int type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private int likeCount;
    private String content;
    private Long commentCount;
}
