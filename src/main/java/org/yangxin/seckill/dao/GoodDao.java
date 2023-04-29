package org.yangxin.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.yangxin.seckill.domain.SecKillGood;
import org.yangxin.seckill.vo.GoodVO;

import java.util.List;

/**
 * @author yangxin
 * 2023/4/8 1:35
 */
@Mapper
public interface GoodDao {

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodVO> listGoodsVo();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodId}")
    GoodVO getGoodVoByGoodId(@Param("goodId") Long goodId);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(SecKillGood secKillGood);
}
