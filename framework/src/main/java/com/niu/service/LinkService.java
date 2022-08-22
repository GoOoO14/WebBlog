package com.niu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-08-22 16:31:38
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}


