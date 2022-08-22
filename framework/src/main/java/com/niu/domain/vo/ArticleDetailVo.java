package com.niu.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo extends ArticleListVo{

    // 分类id
    private Long categoryId;

    //文章内容
    private String content;

}
