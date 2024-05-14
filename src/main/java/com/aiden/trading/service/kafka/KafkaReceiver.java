package com.aiden.trading.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaReceiver {

    /**
     * 下面的主题是一个数组，可以同时订阅多主题，只需按数组格式即可，也就是用","隔开
     */
    @KafkaListener(topics = {"stock_subscribe"})
    public void receive(ConsumerRecord<?, ?> record, Acknowledgment ack){
        try {
            log.info("topic is {}, key is {}, offset is  {},partition is {}", record.topic(), record.key(),record.offset(),record.partition());
            //"301203.SZ": {"time": 1714460700000, "lastPrice": 32.69, "open": 32.27, "high": 32.87, "low": 32.2, "lastClose": 32.39, "amount": 0.0, "volume": 0, "pvolume": 0, "stockStatus": 0, "openInt": 22, "transactionNum": 0, "lastSettlementPrice": 0.0, "settlementPrice": 0.0, "pe": 0.0, "askPrice": [0.0, 0.0, 0.0, 0.0, 0.0], "bidPrice": [0.0, 0.0, 0.0, 0.0, 0.0], "askVol": [0, 0, 0, 0, 0], "bidVol": [0, 0, 0, 0, 0], "volRatio": 0.0, "speed1Min": 0.0, "speed5Min": 0.0}

            // 手动提交offset
            ack.acknowledge();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }
}