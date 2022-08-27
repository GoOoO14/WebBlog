package com.niu.service;

import com.niu.domain.ResponseResult;
import com.niu.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
