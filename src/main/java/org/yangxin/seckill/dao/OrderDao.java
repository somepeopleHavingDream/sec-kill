package org.yangxin.seckill.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.yangxin.seckill.domain.OrderInfo;
import org.yangxin.seckill.domain.SecKillOrder;

/**
 * @author yangxin
 * 2023/4/29 21:49
 */
@SuppressWarnings("UnusedReturnValue")
@Mapper
public interface OrderDao {

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    Long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertSecKillOrder(SecKillOrder secKillOrder);
}
