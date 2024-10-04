package com.yun.psychology.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @author <a href="https://github.com/aguumon">YuanXiao</a>
 * @from <a href="https://github.com/aguumon/yun-psychology-master">git仓库地址</a>
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}