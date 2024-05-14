package com.aiden.trading.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.aiden.trading.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 全局异常拦截
    @ExceptionHandler(NotLoginException.class)
    public Result<?> handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }// 全局异常拦截

    @ExceptionHandler(Exception.class)
    public Result<?> handlerAllException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }
}
