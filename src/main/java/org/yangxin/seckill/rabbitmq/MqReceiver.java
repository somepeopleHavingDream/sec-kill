package org.yangxin.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author yangxin
 * 2023/4/29 13:51
 */
@Service
@Slf4j
public class MqReceiver {

    /**
     * Direct模式 交换机Exchange
     *
     * @param message 消息
     */
    @RabbitListener(queues = MqConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message->{}", message);
    }
}
