package org.yangxin.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
}
