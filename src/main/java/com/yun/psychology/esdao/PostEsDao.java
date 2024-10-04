package com.yun.psychology.esdao;

import com.yun.psychology.model.dto.post.PostEsDTO;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/aguumon">YuanXiao</a>
 * @from <a href="https://github.com/aguumon/yun-psychology-master">git仓库地址</a>
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);
}