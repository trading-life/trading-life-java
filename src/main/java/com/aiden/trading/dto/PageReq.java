package com.aiden.trading.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageReq implements Serializable {
    private Integer page;
    private Integer pageSize;
}
