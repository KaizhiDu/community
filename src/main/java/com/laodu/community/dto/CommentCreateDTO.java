package com.laodu.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private int type;
    private Long commentator;
}
