package com.niu.controller;


import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Article;
import com.niu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mac
 */
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

}






















