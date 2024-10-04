package com.yun.psychology.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.psychology.model.entity.QuestionBank;
import com.yun.psychology.service.QuestionBankService;
import generator.mapper.QuestionBankMapper;
import org.springframework.stereotype.Service;

/**
* @author Yuan
* @description 针对表【question_bank(题库)】的数据库操作Service实现
* @createDate 2024-10-04 16:42:54
*/
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
    implements QuestionBankService{

}




