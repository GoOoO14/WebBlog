package com.niu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.constants.SystemConstants;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Article;
import com.niu.domain.entity.Category;
import com.niu.domain.vo.ArticleListVo;
import com.niu.domain.vo.HotArticleVo;
import com.niu.domain.vo.PageVo;
import com.niu.mapper.ArticleMapper;
import com.niu.service.ArticleService;
import com.niu.service.CategoryService;
import com.niu.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章,封装成ResponseResult返回

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        // 按照浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);

        // 最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        // bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);


        return ResponseResult.okResult(vs);
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件:
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 如果有categoryId, 要求查询时和传入的相同  📢
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&(categoryId>0) ,Article::getCategoryId, categoryId);

        // 状态需要是:正式发
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        // 置顶的文章放在最前面       // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        // 1.分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);


        // 查询categoryName(分类名称
        List<Article> articles = page.getRecords();

        // 1. stream流格式
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
//        articles.stream()
//                .map(new Function<Article, Article>() {
//                    @Override
//                    public Article apply(Article article) {
//                        // 获取分类id, 查询分类信息, 获取分类名称
////                        Category category = categoryService.getById(article.getCategoryId());
////                        String name = category.getName();
////                        String name = categoryService.getById(article.getCategoryId()).getName();
//
//                        // 把分类名称设置给article
////                        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
//                        return article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
//                    }
//                }).collect(Collectors.toList());


        // 2. for循环方式
//        // 在articleId去查找articleName进行设置
//        for (Article article: articles)
//        {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }
}
