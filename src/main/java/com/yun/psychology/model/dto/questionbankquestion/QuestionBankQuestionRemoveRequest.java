package com.yun.psychology.model.dto.questionbankquestion;

import lombok.Data;

/**
 * 创建题库题目关联请求
 *
 * @author <a href="https://github.com/aguumon">YuanXiao</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class QuestionBankQuestionRemoveRequest {

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;


    private static final long serialVersionUID = 1L;

}
