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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Component("syncSHChinaStockJob")
public class SyncSHChinaStockJob implements JobService {

    @Resource
    private AKshareApi aKshareApi;
    @Resource
    private IStockInfoService stockInfoService;

    @Override
    public void run(String params) {
        log.info("\n ======== \n syncChinaStockJob-job-params:{} \n ========", params);
        AkShareReq akShareReq = new AkShareReq();
        akShareReq.setMethod("stock_info_sh_name_code");
        Map<String, Object> args = new HashMap<>();
        akShareReq.setArgs(args);
        args.put("symbol", "主板A股");
        @SuppressWarnings("unchecked")
        AkResult<List<Map<String, Object>>> ret = (AkResult<List<Map<String, Object>>>) aKshareApi.pyMethod(akShareReq);
        if (Objects.equals(ret.getCode(), 0)) {
            if (CollectionUtils.isNotEmpty(ret.getData())) {
                for (Map<String, Object> dataItem : ret.getData()) {
                    try {
                        LambdaQueryWrapper<StockInfo> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(StockInfo::getCode, (String) dataItem.get("code"));
                        queryWrapper.eq(StockInfo::getCurrency, "RMB");
                        queryWrapper.eq(StockInfo::getExchangeCode, "SH");
                        StockInfo stockInfo = new StockInfo();
                        stockInfo.setExchange("上海交易所");
                        stockInfo.setExchangeCode("SH");
                        stockInfo.setCurrency("RMB");
                        stockInfo.setCode((String) dataItem.get("code"));

                        if (Objects.nonNull(dataItem.get("listing_date"))) {
                            // 将时间戳转换为 Instant 对象
                            Instant instant = Instant.ofEpochMilli((Long) dataItem.get("listing_date"));
                            // 获取当前系统默认的时区
                            ZoneId defaultZone = ZoneId.systemDefault();
                            // 将 Instant 对象转换为 LocalDateTime 对象，使用系统默认的时区
                            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, defaultZone);
                            stockInfo.setListDate(localDateTime);
                        }
                        stockInfo.setName((String) dataItem.get("name"));
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
