package com.yun.psychology.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新题目请求
 *
 * @author <a href="https://github.com/aguumon">YuanXiao</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 推荐答案
     */
    private String answer;

    /**
     * 标签列表
     */
    private List<String> tags;
    /**
     * 题目来源
     */
    private String source;
    /**
     * 优先级
     */
    private Integer priority;

    private static final long serialVersionUID = 1L;
}