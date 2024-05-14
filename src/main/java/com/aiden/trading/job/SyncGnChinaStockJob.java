package com.aiden.trading.job;

import com.aiden.trading.config.AKshareApi;
import com.aiden.trading.dto.AkResult;
import com.aiden.trading.dto.akshare.AkShareReq;
import com.aiden.trading.entity.StockGn;
import com.aiden.trading.entity.StockGnItem;
import com.aiden.trading.entity.StockInfo;
import com.aiden.trading.service.IStockGnItemService;
import com.aiden.trading.service.IStockGnService;
import com.aiden.trading.service.IStockInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Slf4j
@Component("syncGnChinaStockJob")
public class SyncGnChinaStockJob implements JobService {

    @Resource
    private AKshareApi aKshareApi;
    @Resource
    private IStockGnService stockGnService;
    @Resource
    private IStockGnItemService stockGnItemService;
    @Resource
    private IStockInfoService stockInfoService;

    @Override
    public void run(String params) {
        log.info("\n ======== \n syncChinaStockJob-job-params:{} \n ========", params);
        AkShareReq akShareReq = new AkShareReq();
        akShareReq.setMethod("stock_board_concept_name_ths");
        Map<String, Object> args = new HashMap<>();
        akShareReq.setArgs(args);
        @SuppressWarnings("unchecked")
        AkResult<List<Map<String, Object>>> ret = (AkResult<List<Map<String, Object>>>) aKshareApi.pyMethod(akShareReq);
        if (Objects.equals(ret.getCode(), 0)) {
            if (CollectionUtils.isNotEmpty(ret.getData())) {
                for (Map<String, Object> dataItem : ret.getData()) {
                    try {
                        LambdaQueryWrapper<StockGn> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(StockGn::getExCode, (String) dataItem.get("代码"));
                        queryWrapper.eq(StockGn::getSource, "THS");
                        StockGn stockInfo = new StockGn();
                        stockInfo.setExCode((String) dataItem.get("代码"));
                        stockInfo.setName((String) dataItem.get("概念名称"));
                        if (Objects.nonNull(dataItem.get("网址"))) {
                            stockInfo.setUrl((String) dataItem.get("网址"));
                        }
                        if (Objects.nonNull(dataItem.get("驱动事件网址")) && StringUtils.isNotBlank((String) dataItem.get("驱动事件网址"))) {
                            stockInfo.setEventUrl((String) dataItem.get("驱动事件网址"));
                        }
                        if (Objects.nonNull(dataItem.get("驱动事件")) && StringUtils.isNotBlank((String) dataItem.get("驱动事件"))) {
                            stockInfo.setEvent((String) dataItem.get("驱动事件"));
                        }
                        stockInfo.setSource("THS");

                        if (Objects.nonNull(dataItem.get("日期"))) {
                            Date date = new Date((Long) dataItem.get("日期"));
                            // 将时间戳转换为 Instant 对象
                            Instant instant = Instant.ofEpochMilli(date.getTime());
                            // 获取当前系统默认的时区
                            ZoneId defaultZone = ZoneId.systemDefault();
                            // 将 Instant 对象转换为 LocalDateTime 对象，使用系统默认的时区
                            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, defaultZone);
                            stockInfo.setListDate(localDateTime);
                        }
                        StockGn stock = stockGnService.getOne(queryWrapper);
                        if (Objects.nonNull(stock)) {
                            stockInfo.setId(stock.getId());
                            stockInfo.setCode(stock.getCode());
                            stockGnService.updateById(stockInfo);
                        } else {
                            stockGnService.save(stockInfo);
                        }
                        //成分股
                        //stock_board_concept_cons_code_ths
                        AkShareReq akShareReq1 = new AkShareReq();
                        akShareReq1.setMethod("stock_board_concept_cons_code_ths");
                        Map<String, Object> args1 = new HashMap<>();
                        akShareReq1.setArgs(args1);
                        args1.put("symbol",(String) dataItem.get("代码"));
                        @SuppressWarnings("unchecked")
                        AkResult<List<Map<String, Object>>> ret1 = (AkResult<List<Map<String, Object>>>) aKshareApi.pyMethod(akShareReq1);

                        LambdaQueryWrapper<StockGnItem> queryWrapper221 = new LambdaQueryWrapper<>();
                        queryWrapper221.eq(StockGnItem::getGnId, stockInfo.getId());
                        stockGnItemService.remove(queryWrapper221);
                        if (Objects.equals(ret1.getCode(), 0)) {
                            if (CollectionUtils.isNotEmpty(ret1.getData())) {
                                for (Map<String, Object> dataItem1 : ret1.getData()) {
                                    String code1 = (String) dataItem1.get("代码");
                                    LambdaQueryWrapper<StockInfo> queryWrapper12 = new LambdaQueryWrapper<>();
                                    queryWrapper12.eq(StockInfo::getCode,code1);
                                    queryWrapper12.and(qw -> qw.eq(StockInfo::getExchangeCode, "SH").or(qw1 -> qw1.eq(StockInfo::getExchangeCode, "SZ")));
                                    StockInfo stockInfo1 = stockInfoService.getOne(queryWrapper12);
                                    if (Objects.nonNull(stockInfo1)) {
                                        LambdaQueryWrapper<StockGnItem> queryWrapper1 = new LambdaQueryWrapper<>();
                                        queryWrapper1.eq(StockGnItem::getGnId, stockInfo.getId());
                                        queryWrapper1.eq(StockGnItem::getStockId, stockInfo1.getId());
                                        StockGnItem exit = stockGnItemService.getOne(queryWrapper1);
                                        if (Objects.nonNull(exit)) {
                                            continue;
                                        }
                                        exit = new StockGnItem();
                                        exit.setStockId(stockInfo1.getId());
                                        exit.setGnId(stock.getId());
                                        stockGnItemService.save(exit);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("dataItem:{}",dataItem.get("代码"),e);
                    }
                }
            }
        }
    }
}
