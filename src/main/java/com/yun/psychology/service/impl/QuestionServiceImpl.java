package com.yun.psychology.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.psychology.model.entity.Question;
import com.yun.psychology.service.QuestionService;
import generator.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author Yuan
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-10-04 16:42:54
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




