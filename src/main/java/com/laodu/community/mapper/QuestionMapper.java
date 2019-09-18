package com.laodu.community.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laodu.community.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    @Select("SELECT * FROM QUESTION")
    public IPage<Question> selectByPage(IPage page, @Param("ew") QueryWrapper queryWrapper);

    @Select("SELECT * FROM QUESTION WHERE TAG REGEXP '#{tag}'")
    public List<Question> selectRelated(Long id, String tag);
}
