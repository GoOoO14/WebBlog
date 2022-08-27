package com.niu.service.Impl;

import com.mysql.cj.log.Log;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.LoginUser;
import com.niu.domain.entity.User;
import com.niu.domain.vo.BlogUserLoginVo;
import com.niu.domain.vo.UserInfoVo;
import com.niu.service.BlogLoginService;
import com.niu.utils.BeanCopyUtils;
import com.niu.utils.JwtUtil;
import com.niu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        // 获取userid, 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId,loginUser);


        // 把 token 和 userinfo 封装成  返回
        // 把user转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {

        // 获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 解析获取userid
        Long userId = loginUser.getUser().getId();

        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+ userId);

        return ResponseResult.okResult();
    }
}
;