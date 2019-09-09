package com.laodu.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laodu.community.entity.User;
import com.laodu.community.mapper.UserMapper;
import com.laodu.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

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
            userMapper.updateById(currentUser);
        }
    }
}
