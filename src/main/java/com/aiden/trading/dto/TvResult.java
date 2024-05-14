package com.aiden.trading.dto;

import com.aiden.trading.constant.TradingViewConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TvResult<T> implements Serializable {

    private String status;
    private T data;

    public TvResult(String status) {
        this.status = status;
    }

    public TvResult(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static TvResult<?> ok() {
        return new TvResult<>(TradingViewConstant.Ok);
    }

    public static <T> TvResult<?> ok(T data) {
        return new TvResult<T>(TradingViewConstant.Ok, data);
    }
}
