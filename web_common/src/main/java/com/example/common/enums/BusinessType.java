package com.example.common.enums;

/**
 * 业务操作类型
 *
 * @author ruoyi
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER("other"),

    /**
     * 新增
     */
    INSERT("insert"),

    /**
     * 修改
     */
    UPDATE("update"),

    /**
     * 删除
     */
    DELETE("delete"),

    /**
     * 授权
     */
    GRANT("grant"),

    /**
     * 导出
     */
    EXPORT("export"),

    /**
     * 导入
     */
    IMPORT("import"),

    /**
     * 强退
     */
    FORCE("force"),

    /**
     * 生成代码
     */
    GENCODE("gencode"),

    /**
     * 清空数据
     */
    CLEAN("clean");


    public final String name;

    BusinessType(String name) {
        this.name = name;
    }
}
