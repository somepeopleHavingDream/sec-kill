package org.yangxin.seckill.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxin
 * 2023/4/29 13:51
 */
@Configuration
public class MqConfig {

    public static final String QUEUE = "queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }
}
