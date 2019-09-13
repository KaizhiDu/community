package com.laodu.community.dto;

import com.laodu.community.entity.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private User user;
    private String createDate;
}
