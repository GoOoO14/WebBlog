package com.niu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.constants.SystemConstants;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Article;
import com.niu.domain.vo.HotArticleVo;
import com.niu.mapper.ArticleMapper;
import com.niu.service.ArticleService;
import com.niu.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


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
}
