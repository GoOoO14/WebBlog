package com.niu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-08-22 12:48:15
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}


