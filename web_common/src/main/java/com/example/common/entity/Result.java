package com.example.common.entity;

import com.example.common.enums.ResultCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    @Schema(description = "响应代码", example = "200")
    private Integer code;

    @Schema(description = "响应信息", example = "成功")
    private String msg;

    @Schema(description = "响应数据", example = "null")
    private T data;

    private Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        Result<T> tResult = new Result<>();
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setMsg(ResultCodeEnum.SUCCESS.msg);
        return tResult;
    }

    public static <T> Result<T> success(T data) {
        Result<T> tResult = new Result<>(data);
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setMsg(ResultCodeEnum.SUCCESS.msg);
        return tResult;
    }


    public static <T> Result<T> error() {
        Result<T> tResult = new Result<>();
        tResult.setCode(ResultCodeEnum.SYSTEM_ERROR.code);
        tResult.setMsg(ResultCodeEnum.SYSTEM_ERROR.msg);
        return tResult;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum) {
        Result<T> tResult = new Result<>();
        tResult.setCode(resultCodeEnum.code);
        tResult.setMsg(resultCodeEnum.msg);
        return tResult;
    }
}
