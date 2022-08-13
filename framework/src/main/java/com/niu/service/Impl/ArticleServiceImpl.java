package com.niu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.domain.entity.Article;
import com.niu.mapper.ArticleMapper;
import com.niu.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


}
