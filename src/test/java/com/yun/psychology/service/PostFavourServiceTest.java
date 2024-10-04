package com.yun.psychology.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.psychology.model.entity.Post;
import com.yun.psychology.model.entity.User;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 帖子收藏服务测试
 *
 * @author <a href="https://github.com/aguumon">YuanXiao</a>
 * @from <a href="https://github.com/aguumon/yun-psychology-master">git仓库地址</a>
 */
@SpringBootTest
class PostFavourServiceTest {

    @Resource
    private PostFavourService postFavourService;

    private static final User loginUser = new User();

    @BeforeAll
    static void setUp() {
        loginUser.setId(1L);
    }

    @Test
    void doPostFavour() {
        int i = postFavourService.doPostFavour(1L, loginUser);
        Assertions.assertTrue(i >= 0);
    }

    @Test
    void listFavourPostByPage() {
        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.eq("id", 1L);
        postFavourService.listFavourPostByPage(Page.of(0, 1), postQueryWrapper, loginUser.getId());
    }
}
