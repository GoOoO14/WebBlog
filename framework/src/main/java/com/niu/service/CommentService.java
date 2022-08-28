package com.niu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.domain.ResponseResult;
import com.niu.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-08-28 13:27:49
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}


