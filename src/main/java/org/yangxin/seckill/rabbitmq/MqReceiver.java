package org.yangxin.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yangxin.seckill.domain.SecKillOrder;
import org.yangxin.seckill.domain.SecKillUser;
import org.yangxin.seckill.service.GoodService;
import org.yangxin.seckill.service.OrderService;
import org.yangxin.seckill.service.SecKillService;
import org.yangxin.seckill.vo.GoodVO;

import java.util.Objects;

/**
 * @author yangxin
 * 2023/4/29 13:51
 */
@Service
@Slf4j
public class MqReceiver {

    private final GoodService goodService;
    private final OrderService orderService;
    private final SecKillService secKillService;

    @Autowired
    public MqReceiver(GoodService goodService, OrderService orderService, SecKillService secKillService) {
        this.goodService = goodService;
        this.orderService = orderService;
        this.secKillService = secKillService;
    }

    /**
     * Direct模式 交换机Exchange
     *
     * @param message 消息
     */
    @RabbitListener(queues = MqConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message->{}", message);
    }

    @RabbitListener(queues = MqConfig.SEC_KILL_QUEUE)
    public void receiveSecKillMessage(String message) {
        log.info("receiveSecKillMessage message->{}", message);

        SecKillMessage secKillMessage = JSON.parseObject(message, SecKillMessage.class);
        SecKillUser user = secKillMessage.getUser();
        Long goodId = secKillMessage.getGoodId();

        // 实际地查表里的数据
        GoodVO goodVO = goodService.getGoodVOByGoodId(goodId);
        int stock = goodVO.getStockCount();
        if (stock <= 0) {
            return;
        }

        /*
            这里需要加锁。
            极端的并发场景下，如果有多个线程同时执行到这里，都判断Object.nonNull(order)为true，则会出现同一个用户重复秒杀的情况
         */

        // 判断是否已经秒杀到了
        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodId(user.getId(), goodId);
        if (Objects.nonNull(order)) {
            return;
        }

        // 减库存，下订单，写入秒杀订单
        secKillService.secKill(user, goodVO);
    }
}
