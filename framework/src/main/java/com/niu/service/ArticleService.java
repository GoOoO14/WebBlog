package com.niu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
