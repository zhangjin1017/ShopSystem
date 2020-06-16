package com.my.service.imp;

import com.my.dao.OrdersMapper;
import com.my.pojo.Orders;
import com.my.pojo.OrdersExample;
import com.my.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImp implements OrdersService {
    private OrdersMapper ordersMapper;

    @Autowired
    public void setOrdersMapper(OrdersMapper ordersMapper) {
        this.ordersMapper = ordersMapper;
    }

    @Override
    public long countByExample(OrdersExample example) {
        return ordersMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(OrdersExample example) {
        return ordersMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return ordersMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(Orders record) {
        return ordersMapper.insert(record);
    }

    @Override
    public int insertSelective(Orders record) {
        return ordersMapper.insertSelective(record);
    }

    @Override
    public List<Orders> selectByExample(OrdersExample example) {
        return ordersMapper.selectByExample(example);
    }

    @Override
    public Orders selectByPrimaryKey(String orderId) {
        return ordersMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public int updateByExampleSelective(Orders record, OrdersExample example) {
        return ordersMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Orders record, OrdersExample example) {
        return updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Orders record) {
        return ordersMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Orders record) {
        return ordersMapper.updateByPrimaryKeySelective(record);
    }
}
