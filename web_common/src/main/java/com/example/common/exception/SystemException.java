package com.example.common.exception;

import com.example.common.enums.ResultCodeEnum;

public class SystemException extends RuntimeException {
    private Integer code;

    public SystemException(ResultCodeEnum codeEnum) {
        super(codeEnum.msg);
        this.code = codeEnum.code;
    }

    // 抛出当前异常对象的时候可以传递其他异常作为原因
    // 通过在异常构造函数中传递 Throwable 对象，可以实现链式异常，提供更详细的错误信息，有助于调试和问题定位。
    // 这种方式在处理复杂错误时非常有用，可以更好地追踪错误的来源。
    public SystemException(ResultCodeEnum codeEnum, Throwable cause) {
        super(codeEnum.msg, cause);
        this.code = codeEnum.code;
    }

    public SystemException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
