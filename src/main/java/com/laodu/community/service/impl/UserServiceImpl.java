package com.laodu.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.entity.User;
import com.laodu.community.exception.CustomizeErrorCode;
import com.laodu.community.exception.CustomizeException;
import com.laodu.community.mapper.UserMapper;
import com.laodu.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrUpdateUser(Long id, User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("ACCOUNT_ID", id);
        User currentUser = userMapper.selectOne(wrapper);
        if (currentUser == null) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            currentUser.setGmtModified(System.currentTimeMillis());
            currentUser.setToken(user.getToken());
            currentUser.setName(user.getName());
            currentUser.setAvatarUrl(user.getAvatarUrl());
            int i = userMapper.updateById(currentUser);
            if (i != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
