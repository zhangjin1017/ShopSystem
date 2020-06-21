package com.my.service.imp;

import com.my.dao.CartMapper;
import com.my.pojo.Cart;
import com.my.pojo.CartExample;
import com.my.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImp implements CartService {
    CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public long countByExample(CartExample example) {
        return cartMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(CartExample example) {
        return cartMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer cartId) {
        return cartMapper.deleteByPrimaryKey(cartId);
    }

    @Override
    public int insert(Cart record) {
        return cartMapper.insert(record);
    }

    @Override
    public int insertSelective(Cart record) {
        return cartMapper.insertSelective(record);
    }

    @Override
    public List<Cart> selectByExample(CartExample example) {
        return cartMapper.selectByExample(example);
    }

    @Override
    public Cart selectByPrimaryKey(Integer cartId) {
        return cartMapper.selectByPrimaryKey(cartId);
    }

    @Override
    public int updateByExampleSelective(Cart record, CartExample example) {
        return cartMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Cart record, CartExample example) {
        return cartMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Cart record) {
        return cartMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Cart record) {
        return cartMapper.updateByPrimaryKey(record);
    }
}
