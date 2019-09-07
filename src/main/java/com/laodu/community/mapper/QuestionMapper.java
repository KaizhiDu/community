package com.laodu.community.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laodu.community.dto.QuestionDTO;
import com.laodu.community.entity.Question;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    //行不通
    public IPage<Question> selectByPage(IPage page, @Param("ew") QueryWrapper queryWrapper);
}
