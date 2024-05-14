package com.aiden.trading.job;

import com.aiden.trading.config.AKshareApi;
import com.aiden.trading.dto.AkResult;
import com.aiden.trading.dto.akshare.AkShareReq;
import com.aiden.trading.entity.StockInfo;
import com.aiden.trading.service.IStockInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Component("syncIndexChinaStockJob")
public class SyncIndexChinaStockJob implements JobService {

    @Resource
    private AKshareApi aKshareApi;
    @Resource
    private IStockInfoService stockInfoService;

    @Override
    public void run(String params) {
        log.info("\n ======== \n syncChinaStockJob-job-params:{} \n ========", params);
        AkShareReq akShareReq = new AkShareReq();
        akShareReq.setMethod("stock_index_list_ths");
        Map<String, Object> args = new HashMap<>();
        akShareReq.setArgs(args);
        @SuppressWarnings("unchecked")
        AkResult<List<Map<String, Object>>> ret = (AkResult<List<Map<String, Object>>>) aKshareApi.pyMethod(akShareReq);
        if (Objects.equals(ret.getCode(), 0)) {
            if (CollectionUtils.isNotEmpty(ret.getData())) {
                for (Map<String, Object> dataItem : ret.getData()) {
                    try {
                        LambdaQueryWrapper<StockInfo> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(StockInfo::getCode, (String) dataItem.get("index_code"));
                        queryWrapper.eq(StockInfo::getCurrency, "RMB");
                        queryWrapper.eq(StockInfo::getExchangeCode, "CHINA");
                        StockInfo stockInfo = new StockInfo();
                        stockInfo.setExchange("中国交易所");
                        stockInfo.setExchangeCode("CHINA");
                        stockInfo.setCurrency("RMB");
                        stockInfo.setCode((String) dataItem.get("index_code"));
                        stockInfo.setCode((String) dataItem.get("index_code"));
                        stockInfo.setThsCode((String) dataItem.get("ths_code"));
                        stockInfo.setName((String) dataItem.get("index_name"));
                        StockInfo stock = stockInfoService.getOne(queryWrapper);
                        if (Objects.nonNull(stock)) {
                            stockInfo.setId(stock.getId());
                            stockInfoService.updateById(stockInfo);
                        }
                        stockInfoService.save(stockInfo);
                    } catch (Exception e) {
                        log.error("{}",dataItem,e);
                    }
                }
            }
        }
    }
}
