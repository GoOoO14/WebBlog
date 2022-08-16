package com.niu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
}
