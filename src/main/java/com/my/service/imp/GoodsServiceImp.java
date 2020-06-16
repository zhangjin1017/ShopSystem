package com.my.service.imp;

import com.my.dao.GoodsMapper;
import com.my.pojo.Goods;
import com.my.pojo.GoodsExample;
import com.my.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImp implements GoodsService {
    private GoodsMapper goodsMapper;

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public long countByExample(GoodsExample example) {
        return goodsMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(GoodsExample example) {
        return goodsMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer goodsId) {
        return goodsMapper.deleteByPrimaryKey(goodsId);
    }

    @Override
    public int insert(Goods record) {
        return goodsMapper.insert(record);
    }

    @Override
    public int insertSelective(Goods record) {
        return goodsMapper.insertSelective(record);
    }

    @Override
    public List<Goods> selectByExample(GoodsExample example) {
        return goodsMapper.selectByExample(example);
    }

    @Override
    public Goods selectByPrimaryKey(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public int updateByExampleSelective(Goods record, GoodsExample example) {
        return goodsMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Goods record, GoodsExample example) {
        return goodsMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Goods record) {
        return goodsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Goods record) {
        return goodsMapper.updateByPrimaryKey(record);
    }
}
