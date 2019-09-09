package com.laodu.community.service;

import com.laodu.community.entity.User;

public interface UserService {
    public void createOrUpdateUser(Long id, User user);
}
