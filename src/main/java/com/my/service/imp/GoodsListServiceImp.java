package com.my.service.imp;

import com.my.dao.GoodsListMapper;
import com.my.pojo.GoodsList;
import com.my.pojo.GoodsListExample;
import com.my.service.GoodsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsListServiceImp implements GoodsListService {

    private GoodsListMapper goodsListMapper;

    @Autowired
    public void setGoodsListMapper(GoodsListMapper goodsListMapper) {
        this.goodsListMapper = goodsListMapper;
    }


    @Override
    public long countByExample(GoodsListExample example) {
        return goodsListMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(GoodsListExample example) {
        return goodsListMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer listId) {
        return goodsListMapper.deleteByPrimaryKey(listId);
    }

    @Override
    public int insert(GoodsList record) {
        return goodsListMapper.insert(record);
    }

    @Override
    public int insertSelective(GoodsList record) {
        return goodsListMapper.insertSelective(record);
    }

    @Override
    public List<GoodsList> selectByExample(GoodsListExample example) {
        return goodsListMapper.selectByExample(example);
    }

    @Override
    public GoodsList selectByPrimaryKey(Integer listId) {
        return goodsListMapper.selectByPrimaryKey(listId);
    }

    @Override
    public int updateByExampleSelective(GoodsList record, GoodsListExample example) {
        return goodsListMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(GoodsList record, GoodsListExample example) {
        return goodsListMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(GoodsList record) {
        return goodsListMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(GoodsList record) {
        return goodsListMapper.updateByPrimaryKey(record);
    }
}
