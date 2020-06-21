package com.my.service.imp;

import com.my.dao.VipCardMapper;
import com.my.pojo.VipCard;
import com.my.pojo.VipCardExample;
import com.my.service.VipCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipCardServiceImp implements VipCardService {
    private VipCardMapper vipCardMapper;

    @Autowired
    public void setVipCardMapper(VipCardMapper vipCardMapper) {
        this.vipCardMapper = vipCardMapper;
    }

    @Override
    public long countByExample(VipCardExample example) {
        return vipCardMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(VipCardExample example) {
        return vipCardMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String cardId) {
        return vipCardMapper.deleteByPrimaryKey(cardId);
    }

    @Override
    public int insert(VipCard record) {
        return vipCardMapper.insert(record);
    }

    @Override
    public int insertSelective(VipCard record) {
        return vipCardMapper.insertSelective(record);
    }

    @Override
    public List<VipCard> selectByExample(VipCardExample example) {
        return vipCardMapper.selectByExample(example);
    }

    @Override
    public VipCard selectByPrimaryKey(String cardId) {
        return vipCardMapper.selectByPrimaryKey(cardId);
    }

    @Override
    public int updateByExampleSelective(VipCard record, VipCardExample example) {
        return vipCardMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(VipCard record, VipCardExample example) {
        return vipCardMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(VipCard record) {
        return vipCardMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(VipCard record) {
        return vipCardMapper.updateByPrimaryKey(record);
    }
}
