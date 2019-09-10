package com.laodu.community.service;

import com.laodu.community.entity.User;

public interface IUserService {
    public void createOrUpdateUser(Long id, User user);
}
