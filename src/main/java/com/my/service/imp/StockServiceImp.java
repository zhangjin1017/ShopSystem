package com.my.service.imp;

import com.my.dao.StockMapper;
import com.my.pojo.Stock;
import com.my.pojo.StockExample;
import com.my.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImp implements StockService {
    private StockMapper stockMapper;

    @Autowired
    public void setStockMapper(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    @Override
    public long countByExample(StockExample example) {
        return stockMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(StockExample example) {
        return stockMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer stockId) {
        return stockMapper.deleteByPrimaryKey(stockId);
    }

    @Override
    public int insert(Stock record) {
        return stockMapper.insert(record);
    }

    @Override
    public int insertSelective(Stock record) {
        return stockMapper.insertSelective(record);
    }

    @Override
    public List<Stock> selectByExample(StockExample example) {
        return stockMapper.selectByExample(example);
    }

    @Override
    public Stock selectByPrimaryKey(Integer stockId) {
        return stockMapper.selectByPrimaryKey(stockId);
    }

    @Override
    public int updateByExampleSelective(Stock record, StockExample example) {
        return stockMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Stock record, StockExample example) {
        return stockMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Stock record) {
        return stockMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Stock record) {
        return stockMapper.updateByPrimaryKey(record);
    }
}
