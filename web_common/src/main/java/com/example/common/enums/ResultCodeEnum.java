package com.example.common.enums;

public enum ResultCodeEnum {
    //常量对象
    SUCCESS(200, "成功"),
    PARAM_ERROR(400, "参数异常"),
    TOKEN_INVALID_ERROR(401, "无效的token"),
    USER_SECURITY_ERROR(401, "用户权限不够"),
    TOKEN_CHECK_ERROR(401, "token验证失败，请重新登录"),
    PARAM_LOST_ERROR(4001, "参数缺失"),
    CAPTCHA_ERROR(4002, "验证码错误"),
    PASSWORD_ERROR(4002, "密码错误"),
    BAN_OPERATE_SUPER_ADMIN_ERROR(4003, "禁止操作超级管理员"),
    USER_ADD_ROLE_EXIST(4004, "用户已分配当前角色"),

    SYSTEM_ERROR(500, "系统异常"),
    USER_EXIST_ERROR(5001, "用户名已存在"),
    USER_NOT_LOGIN(5002, "用户未登录"),
    USER_User_ERROR(5003, "密码错误"),
    USER_NOT_EXIST_ERROR(5004, "用户不存在"),
    PARAM_PASSWORD_ERROR(5005, "原密码输入错误"),
    FILES_UPLOAD_ERROR(5006, "文件上传失败"),
    NO_DATA_ERROR(5007, "无数据"),
    DOCS_EXIST_ERROR(5008, "文档标题已存在"),
    LOG_ERROR(5009, "服务器记录日志异常，请联系管理员"),
    EXCEL_ERROR(5010, "导出excel异常"),
    ;

    public final Integer code;
    public final String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
