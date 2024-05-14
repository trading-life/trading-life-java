package com.aiden.trading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
public class PageResp<T> implements Serializable {
    private List<T> items;
    private Long total;
}
