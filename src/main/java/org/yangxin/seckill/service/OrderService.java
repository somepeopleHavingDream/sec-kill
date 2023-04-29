package org.yangxin.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yangxin.seckill.dao.OrderDao;
import org.yangxin.seckill.domain.OrderInfo;
import org.yangxin.seckill.domain.SecKillOrder;
import org.yangxin.seckill.domain.SecKillUser;
import org.yangxin.seckill.redis.OrderKey;
import org.yangxin.seckill.redis.RedisService;
import org.yangxin.seckill.vo.GoodVO;

import java.util.Date;

/**
 * @author yangxin
 * 2023/4/29 21:03
 */
@Service
public class OrderService {

    private final RedisService redisService;

    private final OrderDao orderDao;

    @Autowired
    public OrderService(RedisService redisService, OrderDao orderDao) {
        this.redisService = redisService;
        this.orderDao = orderDao;
    }

    public SecKillOrder getSecKillOrderByUserIdGoodId(Long userId, Long goodId) {
        return redisService.get(OrderKey.getSecKillOrderByUidGid, "" + userId + "_" + goodId, SecKillOrder.class);
    }

    @Transactional(rollbackFor = Throwable.class)
    public OrderInfo createOrder(SecKillUser user, GoodVO goodVO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodVO.getId());
        orderInfo.setGoodsName(goodVO.getGoodsName());
        orderInfo.setGoodsPrice(goodVO.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);

        SecKillOrder secKillOrder = new SecKillOrder();
        secKillOrder.setGoodsId(goodVO.getId());
        secKillOrder.setOrderId(orderInfo.getId());
        secKillOrder.setUserId(user.getId());
        orderDao.insertSecKillOrder(secKillOrder);

        redisService.set(OrderKey.getSecKillOrderByUidGid, "" + user.getId() + "_" + goodVO.getId(), secKillOrder);

        return orderInfo;
    }
}
