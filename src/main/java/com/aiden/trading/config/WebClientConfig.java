package com.aiden.trading.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
@Configuration
public class WebClientConfig {
    @Value("${remote.baseUrl}")
    private String baseUrl;

//    @Value("${remote.xtquantUrl}")
//    private String xtquantUrl;
//    @Autowired
//    private TokenHolder tokenHolder;

    @Bean
    public WebClient webClient() {
        Integer time = 1000 * 60 * 100;
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, time)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(time, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(time, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                //添加全局默认请求头
                .defaultHeader("Content-Type", "application/json;charset=UTF-8")
                //给请求添加过滤器，添加自定义的认证头
                .filter((request, next) -> {
                    ClientRequest filtered = ClientRequest.from(request)
//                            .header("Authorization", tokenHolder.getToken())
                            .build();
                    return next.exchange(filtered);
                })
                .baseUrl(baseUrl)

                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))

                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(10 * 1024 * 1024))
                        .build())

                .build();
    }

    @Bean
    public AKshareApi aKshareApi(WebClient webClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).blockTimeout(Duration.ofSeconds(600)).build();
        return factory.createClient(AKshareApi.class);
    }
}
