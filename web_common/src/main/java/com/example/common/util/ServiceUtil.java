package com.example.common.util;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;

/**
 * 服务层公共工具方法
 */
public final class ServiceUtil {

    private ServiceUtil() {
    }

    /**
     * 判断数据库操作是否成功，影响行数为0则抛出系统异常
     *
     * @param affectedRows 影响行数
     */
    public static void checkSuccess(Integer affectedRows) {
        if (affectedRows == 0) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
