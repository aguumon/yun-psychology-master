package com.yun.psychology.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.psychology.annotation.AuthCheck;
import com.yun.psychology.common.BaseResponse;
import com.yun.psychology.common.DeleteRequest;
import com.yun.psychology.common.ErrorCode;
import com.yun.psychology.common.ResultUtils;
import com.yun.psychology.constant.UserConstant;
import com.yun.psychology.exception.BusinessException;
import com.yun.psychology.exception.ThrowUtils;
import com.yun.psychology.model.dto.questionbankquestion.QuestionBankQuestionAddRequest;
import com.yun.psychology.model.dto.questionbankquestion.QuestionBankQuestionQueryRequest;
import com.yun.psychology.model.dto.questionbankquestion.QuestionBankQuestionRemoveRequest;
import com.yun.psychology.model.dto.questionbankquestion.QuestionBankQuestionUpdateRequest;
import com.yun.psychology.model.entity.QuestionBankQuestion;
import com.yun.psychology.model.entity.User;
import com.yun.psychology.model.vo.QuestionBankQuestionVO;
import com.yun.psychology.service.QuestionBankQuestionService;
import com.yun.psychology.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题库题目关联接口
 *
 * @author <a href="https://github.com/aguumon">YuanXiao</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/questionBankQuestion")
@Slf4j
public class QuestionBankQuestionController {

    @Resource
    private QuestionBankQuestionService questionBankQuestionService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建题库题目关联
     *
     * @param questionBankQuestionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestionBankQuestion(@RequestBody QuestionBankQuestionAddRequest questionBankQuestionAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionBankQuestionAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 DTO 进行转换
        QuestionBankQuestion questionBankQuestion = new QuestionBankQuestion();
        BeanUtils.copyProperties(questionBankQuestionAddRequest, questionBankQuestion);
        // 数据校验
        questionBankQuestionService.validQuestionBankQuestion(questionBankQuestion, true);
        // todo 填充默认值
        User loginUser = userService.getLoginUser(request);
        questionBankQuestion.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = questionBankQuestionService.save(questionBankQuestion);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newQuestionBankQuestionId = questionBankQuestion.getId();
        return ResultUtils.success(newQuestionBankQuestionId);
    }

    @PostMapping("/remove")
    public BaseResponse<Boolean> removeQuestionBankQuestion(@RequestBody QuestionBankQuestionRemoveRequest removeRequest){
        ThrowUtils.throwIf(removeRequest == null
                || removeRequest.getQuestionId() == null
                || removeRequest.getQuestionBankId() == null, ErrorCode.PARAMS_ERROR);
        Long questionId = removeRequest.getQuestionId();
        Long questionBankId = removeRequest.getQuestionBankId();
        LambdaQueryWrapper<QuestionBankQuestion> queryWrapper = Wrappers.lambdaQuery(QuestionBankQuestion.class);
        queryWrapper.eq(QuestionBankQuestion::getQuestionId,questionId)
                .eq(QuestionBankQuestion::getQuestionBankId,questionBankId);
        // 操作数据库
        boolean result = questionBankQuestionService.remove(queryWrapper);

        return ResultUtils.success(result);
    }


    /**
     * 删除题库题目关联
     *
     * @param deleteRequest
     * @param request
     * @return
     */
//    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestionBankQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        QuestionBankQuestion oldQuestionBankQuestion = questionBankQuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestionBankQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestionBankQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = questionBankQuestionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }


    /**
     * 根据 id 获取题库题目关联（封装类）
     *
     * @param id
     * @return
     */
//    @GetMapping("/get/vo")
    public BaseResponse<QuestionBankQuestionVO> getQuestionBankQuestionVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        QuestionBankQuestion questionBankQuestion = questionBankQuestionService.getById(id);
        ThrowUtils.throwIf(questionBankQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(questionBankQuestionService.getQuestionBankQuestionVO(questionBankQuestion, request));
    }

    /**
     * 分页获取题库题目关联列表（仅管理员可用）
     *
     * @param questionBankQuestionQueryRequest
     * @return
     */
//    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<QuestionBankQuestion>> listQuestionBankQuestionByPage(@RequestBody QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest) {
        long current = questionBankQuestionQueryRequest.getCurrent();
        long size = questionBankQuestionQueryRequest.getPageSize();
        // 查询数据库
        Page<QuestionBankQuestion> questionBankQuestionPage = questionBankQuestionService.page(new Page<>(current, size),
                questionBankQuestionService.getQueryWrapper(questionBankQuestionQueryRequest));
        return ResultUtils.success(questionBankQuestionPage);
    }

    /**
     * 分页获取题库题目关联列表（封装类）
     *
     * @param questionBankQuestionQueryRequest
     * @param request
     * @return
     */
//    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionBankQuestionVO>> listQuestionBankQuestionVOByPage(@RequestBody QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest,
                                                               HttpServletRequest request) {
        long current = questionBankQuestionQueryRequest.getCurrent();
        long size = questionBankQuestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBankQuestion> questionBankQuestionPage = questionBankQuestionService.page(new Page<>(current, size),
                questionBankQuestionService.getQueryWrapper(questionBankQuestionQueryRequest));
        // 获取封装类
        return ResultUtils.success(questionBankQuestionService.getQuestionBankQuestionVOPage(questionBankQuestionPage, request));
    }

    /**
     * 分页获取当前登录用户创建的题库题目关联列表
     *
     * @param questionBankQuestionQueryRequest
     * @param request
     * @return
     */
//    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionBankQuestionVO>> listMyQuestionBankQuestionVOByPage(@RequestBody QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(questionBankQuestionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        questionBankQuestionQueryRequest.setUserId(loginUser.getId());
        long current = questionBankQuestionQueryRequest.getCurrent();
        long size = questionBankQuestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBankQuestion> questionBankQuestionPage = questionBankQuestionService.page(new Page<>(current, size),
                questionBankQuestionService.getQueryWrapper(questionBankQuestionQueryRequest));
        // 获取封装类
        return ResultUtils.success(questionBankQuestionService.getQuestionBankQuestionVOPage(questionBankQuestionPage, request));
    }

    // endregion
}
