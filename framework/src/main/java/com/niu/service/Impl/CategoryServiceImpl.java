package com.niu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.constants.SystemConstants;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Article;
import com.niu.domain.vo.CategoryVo;
import com.niu.mapper.CategoryMapper;
import com.niu.service.ArticleService;
import com.niu.service.CategoryService;
import com.niu.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.niu.domain.entity.Category;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-08-22 12:48:15
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {

        // 这里我们使用分布查询,先查询一个表,再查询另一个表
        // 1. 查询文章表,状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        // 2. 获取文章的分类id,并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(new Function<Article, Long>() {
                    @Override
                    public Long apply(Article article) {
                        return article.getCategoryId();
                    }
                }).collect(Collectors.toSet());

        // 3. 查询分类表
        List<Category> categories = listByIds(categoryIds);


        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                        .collect(Collectors.toList());


        // 4. 封装成vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}


