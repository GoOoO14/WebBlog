package com.niu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.domain.entity.User;
import com.niu.mapper.UserMapper;
import com.niu.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-08-28 14:18:16
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}


