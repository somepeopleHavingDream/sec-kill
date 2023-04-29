package org.yangxin.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yangxin.seckill.dao.GoodDao;
import org.yangxin.seckill.domain.SecKillGood;
import org.yangxin.seckill.vo.GoodVO;

import java.util.List;

/**
 * @author yangxin
 * 2023/4/8 1:33
 */
@Service
public class GoodService {

    private final GoodDao goodDao;

    @Autowired
    public GoodService(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    public List<GoodVO> listGoodsVo(){
        return goodDao.listGoodsVo();
    }

    public GoodVO getGoodVOByGoodId(Long goodId) {
        return goodDao.getGoodVoByGoodId(goodId);
    }

    public boolean reduceStock(GoodVO goodVO) {
        SecKillGood secKillGood = new SecKillGood();
        secKillGood.setGoodsId(goodVO.getId());

        int ret = goodDao.reduceStock(secKillGood);
        return ret > 0;
    }
}
