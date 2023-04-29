package org.yangxin.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangxin
 * 2023/4/29 13:50
 */
@Service
@Slf4j
public class MqSender {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public MqSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void send(Object message) {
        String msg = JSON.toJSONString(message);
        log.info("send msg->{}", msg);

        amqpTemplate.convertAndSend(MqConfig.QUEUE, msg);
    }

    public void sendSecKillMessage(SecKillMessage secKillMessage) {
        String msg = JSON.toJSONString(secKillMessage);
        log.info("sendSecKillMessage secKillMessage->{}", msg);

        amqpTemplate.convertAndSend(MqConfig.SEC_KILL_QUEUE, msg);
    }
}
