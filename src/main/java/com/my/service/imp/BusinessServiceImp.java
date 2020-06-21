package com.my.service.imp;

import com.my.dao.BusinessMapper;
import com.my.pojo.Business;
import com.my.pojo.BusinessExample;
import com.my.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImp implements BusinessService {

    private BusinessMapper businessMapper;

    @Autowired
    public void setBusinessMapper(BusinessMapper businessMapper) {
        this.businessMapper = businessMapper;
    }

    @Override
    public long countByExample(BusinessExample example) {
        return businessMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(BusinessExample example) {
        return businessMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer businessId) {
        return businessMapper.deleteByPrimaryKey(businessId);
    }

    @Override
    public int insert(Business record) {
        return businessMapper.insert(record);
    }

    @Override
    public int insertSelective(Business record) {
        return businessMapper.insertSelective(record);
    }

    @Override
    public List<Business> selectByExample(BusinessExample example) {
        return businessMapper.selectByExample(example);
    }

    @Override
    public Business selectByPrimaryKey(Integer businessId) {
        return businessMapper.selectByPrimaryKey(businessId);
    }

    @Override
    public int updateByExampleSelective(Business record, BusinessExample example) {
        return businessMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Business record, BusinessExample example) {
        return businessMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Business record) {
        return businessMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Business record) {
        return businessMapper.updateByPrimaryKey(record);
    }
}
