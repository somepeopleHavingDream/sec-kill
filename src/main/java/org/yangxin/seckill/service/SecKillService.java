package org.yangxin.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yangxin.seckill.domain.OrderInfo;
import org.yangxin.seckill.domain.SecKillUser;
import org.yangxin.seckill.redis.RedisService;
import org.yangxin.seckill.redis.SecKillKey;
import org.yangxin.seckill.vo.GoodVO;

/**
 * @author yangxin
 * 2023/4/29 21:35
 */
@SuppressWarnings("UnusedReturnValue")
@Service
public class SecKillService {

    private final GoodService goodService;
    private final OrderService orderService;

    private final RedisService redisService;

    @Autowired
    public SecKillService(GoodService goodService, OrderService orderService, RedisService redisService) {
        this.goodService = goodService;
        this.orderService = orderService;
        this.redisService = redisService;
    }

    @Transactional(rollbackFor = Throwable.class)
    public OrderInfo secKill(SecKillUser user, GoodVO goodVO) {
        // 减库存，下订单，写入秒杀订单
        boolean success = goodService.reduceStock(goodVO);
        if (success) {
            return orderService.createOrder(user, goodVO);
        } else {
            setGoodOver(goodVO.getId());
            return null;
        }
    }

    private void setGoodOver(Long goodId) {
        redisService.set(SecKillKey.isGoodOver, "" + goodId, true);
    }
}
