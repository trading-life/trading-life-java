package com.aiden.trading.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.aiden.trading.constant.TradingViewConstant;
import com.aiden.trading.dto.Result;
import com.aiden.trading.dto.TvResult;
import com.aiden.trading.dto.tradingview.ChartInfo;
import com.aiden.trading.dto.tradingview.StudyTemplateInfo;
import com.aiden.trading.dto.tradingview.req.SaveChartReq;
import com.aiden.trading.dto.tradingview.req.SaveDrawingReq;
import com.aiden.trading.dto.tradingview.req.SaveDrawingTemplateReq;
import com.aiden.trading.dto.tradingview.req.SaveStudyTemplateReq;
import com.aiden.trading.dto.tradingview.resp.*;
import com.aiden.trading.entity.TvChartInfo;
import com.aiden.trading.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/tradingview")
@Tag(name = "tradingview接口")
@CrossOrigin
public class TradingViewController {

    @Resource
    private ITvStudyTemplateInfoService tvStudyTemplateInfoService;

    @Resource
    private ITvChartInfoService tvChartInfoService;
    @Resource
    private ITvKlineMarkService tvKlineMarkService;
    @Resource
    private ITvDrawingTemplatesService drawingTemplatesService;
    @Resource
    private ITvDrawingService drawingService;

    /**
     * {
     * supports_search: true,
     * supports_group_request: false,
     * supports_marks: true,
     * exchanges: [
     * {value: "", name: "All Exchanges", desc: ""},
     * {value: "XETRA", name: "XETRA", desc: "XETRA"},
     * {value: "NSE", name: "NSE", desc: "NSE"}
     * ],
     * symbolsTypes: [
     * {name: "All types", value: ""},
     * {name: "Stock", value: "stock"},
     * {name: "Index", value: "index"}
     * ],
     * supportedResolutions: [ "1", "15", "30", "60", "D", "2D", "3D", "W", "3W", "M", '6M' ]
     * };
     */
    @GetMapping("/config")
    public ConfigurationResp config() {
        ConfigurationResp configurationResp = new ConfigurationResp();
        configurationResp.setSupportsSearch(true);
        configurationResp.setSupportsGroupRequest(false);
        configurationResp.setSupportsMarks(true);
        configurationResp.setSupportsTime(true);
        configurationResp.setSupportsTimescaleMarks(true);
        configurationResp.setExchanges(List.of(new ConfigurationResp.ExchangesDTO("", "All Exchanges", "")));
        configurationResp.setSymbolsTypes(List.of(new ConfigurationResp.SymbolsTypesDTO("All types", "")));
        configurationResp.setSupportedResolutions(List.of("1T", "5T", "100T",//tick
                "1S", "2S", "100S",//second
                "1", "2", "100",//minute
                "60", "120", "240",//hour
                "1D", "2D", "100D",//day
                "1W", "2W", "240W",//week
                "1M", "2M", "100M",//month
                "12M", "24M", "48M"//year
                ));
        return configurationResp;
    }

    @GetMapping("/time")
    public long time() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * GET /charts_storage_api_version/study_templates?client=client_id&user=user_id
     * GET /charts_storage_api_version/study_tempates?client=client_id&user=user_id&chart=chart_id&template=name
     * template：模板名称
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @GetMapping("/1.0/study_templates")
    public TvResult<?> studyTemplatesV1(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam(value = "template", required = false) String template) {
        if (StringUtils.isNoneBlank(template)) {
            TvResult<StudyTemplateInfo> ret = new TvResult<>();
            StudyTemplateInfo studyTemplateInfo = tvStudyTemplateInfoService.getByName(template, user, client);
            ret.setStatus(TradingViewConstant.Ok);
            ret.setData(studyTemplateInfo);
            return ret;
        }
        TvResult<List<Map<String, Object>>> ret = new TvResult<>();
        ret.setStatus(TradingViewConstant.Ok);
        ret.setData(tvStudyTemplateInfoService.getStudyTemplateNames(user, client));
        return ret;
    }


    /**
     * post /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @PostMapping("/1.0/study_templates")
    public TvResult<?> postStudyTemplatesV1(SaveStudyTemplateReq saveStudyTemplateReq) {
        tvStudyTemplateInfoService.saveStudyTemplate(saveStudyTemplateReq);
        return TvResult.ok();
    }

    /**
     * delete /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @DeleteMapping("/1.0/study_templates")
    public TvResult<?> deleteStudyTemplatesV1(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam("template") String template) {
        tvStudyTemplateInfoService.deleteStudyTemplateByName(client, user, template);
        return TvResult.ok();
    }

    /**
     * status：ok 或 error
     * data：对象数组
     * timestamp：保存图表的 UNIX 时间（例如，1449084321）
     * symbol：图表的商品ID（例如，AA）
     * resolution：图表的分辨率（例如，1D）
     * id：图表的唯一整数标识符（例如，9163）
     * name：图表名称（例如，Test
     */
    @GetMapping("/1.0/charts")
    public TvResult<?> charts(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam(value = "chart", required = false) Integer chart) {
        if (Objects.nonNull(chart)) {
            TvResult<ChartInfo> reta = new TvResult<>();
            ChartInfo studyTemplateInfo = tvChartInfoService.getChartInfoById(chart, user, client);
            reta.setStatus(TradingViewConstant.Ok);
            reta.setData(studyTemplateInfo);
            return reta;
        }
        List<Map<String, Object>> retData = tvChartInfoService.getChartInfos(user, client);
        TvResult<List<Map<String, Object>>> ret = new TvResult<>();

        ret.setStatus(TradingViewConstant.Ok);
        ret.setData(retData);
        return ret;
    }


    /**
     * post /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @PostMapping("/1.0/charts")
    public Map<String, Object> postCharts(SaveChartReq saveChartReq) {
        TvChartInfo tvChartInfo = tvChartInfoService.saveChart(saveChartReq);
        Map<String, Object> ret = new HashMap<>();
        ret.put("status", TradingViewConstant.Ok);
        if (Objects.isNull(saveChartReq.getChart())) {
            ret.put("id", tvChartInfo.getId());
        }
        return ret;
    }

    /**
     * delete /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @DeleteMapping("/1.0/charts")
    public TvResult<?> deleteCharts(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam("chart") Integer chart) {
        tvChartInfoService.deleteChart(client, user, chart);
        return TvResult.ok();
    }

    /**
     * status：ok 或 error
     * data：对象数组
     * timestamp：保存图表的 UNIX 时间（例如，1449084321）
     * symbol：图表的商品ID（例如，AA）
     * resolution：图表的分辨率（例如，1D）
     * id：图表的唯一整数标识符（例如，9163）
     * name：图表名称（例如，Test
     * drawing_templates?client=trading_platform_demo&user=public_user&tool=LineToolRay&name=c
     */
    @GetMapping("/1.0/drawing_templates")
    public TvResult<?> getDrawingTemplates(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam(value = "tool", required = false) String tool, @RequestParam(value = "name", required = false) String name) {
        if (StringUtils.isNotBlank(name)) {
            TvResult<DrawingTemplatesInfo> reta = new TvResult<>();
            DrawingTemplatesInfo drawingTemplatesInfo = drawingTemplatesService.getInfoByToolAndName(user, client, tool, name);
            reta.setStatus(TradingViewConstant.Ok);
            reta.setData(drawingTemplatesInfo);
            return reta;
        }
        TvResult<List<String>> ret = new TvResult<>();
        ret.setStatus(TradingViewConstant.Ok);
        ret.setData(drawingTemplatesService.getInfosByTool(user, client, tool));
        return ret;
    }


    /**
     * post /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @PostMapping("/1.0/drawing_templates")
    public TvResult<?> postDrawingTemplates(SaveDrawingTemplateReq saveDrawingTemplateReq) {
        drawingTemplatesService.saveDrawingTemplate(saveDrawingTemplateReq);
        return TvResult.ok();
    }

    /**
     * delete /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @DeleteMapping("/1.0/drawing_templates")
    public TvResult<?> deleteDrawingTemplates(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam(value = "tool", required = false) String tool, @RequestParam(value = "name", required = false) String name) {
        drawingTemplatesService.deleteByToolAndName(user, client, tool, name);
        return TvResult.ok();
    }

    /**
     * status：ok 或 error
     * data：对象数组
     * timestamp：保存图表的 UNIX 时间（例如，1449084321）
     * symbol：图表的商品ID（例如，AA）
     * resolution：图表的分辨率（例如，1D）
     * id：图表的唯一整数标识符（例如，9163）
     * name：图表名称（例如，Test
     * drawing_templates?client=trading_platform_demo&user=public_user&tool=LineToolRay&name=c
     */
    @GetMapping("/1.0/drawings")
    public TvResult<?> getDrawings(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam(value = "tool", required = false) String tool, @RequestParam(value = "name", required = false) String name) {
        return TvResult.ok();
    }


    /**
     * post /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @PostMapping("/1.0/drawings")
    public TvResult<?> postDrawings(SaveDrawingReq saveDrawingReq) {
        drawingService.saveDrawing(saveDrawingReq);
        return TvResult.ok();
    }

    /**
     * delete /charts_storage_api_version/charts?client=client_id&user=user_id
     * status	ok or error
     * data	Array of objects where each object has a name property representing the template name (example: Test)
     */
    @DeleteMapping("/1.0/drawings")
    public TvResult<?> deleteDrawings(@RequestParam("client") String client, @RequestParam("user") String user, @RequestParam(value = "tool", required = false) String tool, @RequestParam(value = "name", required = false) String name) {
        return TvResult.ok();
    }

    /**
     * GET /marks?symbol=<ticker_name>&from=<unix_timestamp>&to=<unix_timestamp>&resolution=<resolution>
     */
    @GetMapping("/marks")
    public MarksResp marks(@RequestParam("symbol") String symbol, @RequestParam("resolution") String resolution, @RequestParam("from") Long from, @RequestParam("to") Long to) {
        return tvKlineMarkService.selectKlineMark(symbol, resolution, from, to);
    }

    /**
     * GET /quotes?symbols=<ticker_name_1>,<ticker_name_2>,...,<ticker_name_n>
     */
    @GetMapping("quotes")
    public QuotesResp quotes(@RequestParam("symbols") List<String> symbols) {
        QuotesResp quotesResp = new QuotesResp();
        quotesResp.setS(TradingViewConstant.Ok);
        List<QuotesResp.Datas> datas = new ArrayList<>();
        quotesResp.setD(datas);
        QuotesResp.Datas datas1 = new QuotesResp.Datas();
        datas1.setS(TradingViewConstant.Ok);
        datas1.setN("AAPL");
        datas.add(datas1);
        QuotesResp.Datas.DataV dataV = new QuotesResp.Datas.DataV();
        datas1.setV(dataV);
        dataV.setCh(new BigDecimal("0.1"));
        dataV.setChp(new BigDecimal("0.1"));
        dataV.setShortName("AAPL");
        dataV.setExchange("AAPL");
        dataV.setOriginalName("AAPL");
        dataV.setDescription("AAPL");
        dataV.setLp(new BigDecimal("0.1"));
        dataV.setAsk(new BigDecimal("0.1"));
        dataV.setBid(new BigDecimal("0.1"));
        dataV.setOpenPrice(new BigDecimal("0.1"));
        dataV.setHighPrice(new BigDecimal("0.1"));
        dataV.setLowPrice(new BigDecimal("0.1"));
        dataV.setPrevClosePrice(new BigDecimal("0.1"));
        dataV.setVolume(new BigDecimal("0.1"));
        return quotesResp;
    }

    /**
     * GET /symbols?symbol=AAL, GET /symbols?symbol=NYSE:MSFT
     * symbol: string. Symbol name or ticker.
     */
    @GetMapping("/symbols")
    public SymbolInfoResp symbol(@RequestParam("symbol") String symbol) {
        SymbolInfoResp symbolInfoResp = new SymbolInfoResp();
        symbolInfoResp.setName("APPL");
        symbolInfoResp.setExchangetraded("NasdaqNM");
        symbolInfoResp.setExchangelisted("NasdaqNM");
        symbolInfoResp.setTimezone("America/New_York");
        symbolInfoResp.setMinmov(1);
        symbolInfoResp.setMinmov2(2);
        symbolInfoResp.setPointvalue(1);
        symbolInfoResp.setSession("0930-1630");
        symbolInfoResp.setHasIntraday(false);
        symbolInfoResp.setVisiblePlotsSet("ohlcv");
        symbolInfoResp.setDescription("Apple Inc.");
        symbolInfoResp.setType("stock");
        symbolInfoResp.setSupportedResolutions(Arrays.asList("D", "2D", "3D", "W", "3W", "M", "6M"));
        symbolInfoResp.setPricescale(100);
        symbolInfoResp.setTicker("APPL");
        symbolInfoResp.setLogoUrls(List.of("https://s3-symbol-logo.tradingview.com/apple.svg"));
        symbolInfoResp.setExchangeLogo("https://s3-symbol-logo.tradingview.com/country/US.svg");
        return symbolInfoResp;
    }

    /**
     * GET /history?symbol=<ticker_name>&from=<unix_timestamp>&to=<unix_timestamp>&resolution=<resolution>&countback=<countback>
     * symbol: 商品ID
     * from: unix timestamp (UTC) 最左侧所需K线的 unix 时间戳
     * to: unix timestamp (UTC) 最右边的所需K线（不包括在内）
     * resolution: string
     * countback: 以 to 开头的k线（优先级高于 from ）。 如果设置了 countback，则应该忽略 from
     * <p>
     * s: 状态码。 预期值:ok|error|no_data
     * errmsg: 错误消息。只在s = 'error'时出现
     * t: K线时间. unix时间戳 (UTC)
     * c: 收盘价
     * o: 开盘价 (可选)
     * h: 最高价 (可选)
     * l: 最低价(可选)
     * v: 成交量 (可选)
     */
    @GetMapping("/history")
    public HistoryResp history(@RequestParam("symbol") String symbol, @RequestParam("resolution") String resolution, @RequestParam("from") Long from, @RequestParam("to") Long to, @RequestParam("countback") Long countback) {

        HistoryResp ret = new HistoryResp();
        if (from < 1475452800) {
            ret.setC(List.of());
            ret.setH(List.of());
            ret.setL(List.of());
            ret.setO(List.of());
            ret.setT(List.of());
            ret.setV(List.of());
            ret.setS("no_data");
            return ret;
        }
        ret.setC(List.of(106.0, 106.1));
        ret.setH(List.of(106.5, 106.5699));
        ret.setL(List.of( 105.5, 105.64));
        ret.setO(List.of(105.8, 105.66));
        ret.setT(List.of(1472515200, 1472601600));
        ret.setV(List.of(24863945, 29662406));

        ret.setS("ok");
        return ret;
    }

    /**
     * GET /history?symbol=<ticker_name>&from=<unix_timestamp>&to=<unix_timestamp>&resolution=<resolution>&countback=<countback>
     * symbol: 商品ID
     * from: unix timestamp (UTC) 最左侧所需K线的 unix 时间戳
     * to: unix timestamp (UTC) 最右边的所需K线（不包括在内）
     * resolution: string
     * countback: 以 to 开头的k线（优先级高于 from ）。 如果设置了 countback，则应该忽略 from
     * <p>
     * s: 状态码。 预期值:ok|error|no_data
     * errmsg: 错误消息。只在s = 'error'时出现
     * t: K线时间. unix时间戳 (UTC)
     * c: 收盘价
     * o: 开盘价 (可选)
     * h: 最高价 (可选)
     * l: 最低价(可选)
     * v: 成交量 (可选)
     */
    @GetMapping("/timescale_marks")
    public List<?> timescale_marks(@RequestParam("symbol") String symbol, @RequestParam("resolution") String resolution, @RequestParam("from") Long from, @RequestParam("to") Long to) {
        return Collections.EMPTY_LIST;
    }
    @GetMapping("/isFirst")
    public Result<Boolean> isFirst(@RequestParam("clientId") String clientId) {
        if (StpUtil.isLogin()) {
            Boolean isFirst = tvChartInfoService.isFirst((String)StpUtil.getLoginId(),clientId);
            return Result.data(isFirst);
        }
        return Result.data(true);
    }

}
