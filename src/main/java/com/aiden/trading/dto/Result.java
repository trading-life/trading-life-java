package com.aiden.trading.dto;

import com.aiden.trading.constant.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private T result;

    public static Result<?> ok() {
        return new Result<>(ResultEnum.CODE_SUCCESS.getCode(), ResultEnum.CODE_SUCCESS.getDesc(), (Object) null);
    }

    public static Result<?> ok(String msg) {
        return new Result<>(ResultEnum.CODE_SUCCESS.getCode(), msg, (Object) null);
    }

    public static Result<?> code(int code) {
        return new Result<>(code, (String) null, (Object) null);
    }

    public static <T> Result<T> data(T result) {
        return new Result<T>(ResultEnum.CODE_SUCCESS.getCode(), ResultEnum.CODE_SUCCESS.getDesc(), result);
    }

    public static Result<?> error() {
        return new Result<>(ResultEnum.CODE_ERROR.getCode(), ResultEnum.CODE_ERROR.getDesc(), (Object) null);
    }

    public static Result<?> error(String msg) {
        return new Result<>(ResultEnum.CODE_ERROR.getCode(), msg, (Object) null);
    }

    public static <T> Result<T> get(int code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

}
