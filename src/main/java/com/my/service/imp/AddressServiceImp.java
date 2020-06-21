package com.my.service.imp;

import com.my.dao.AddressMapper;
import com.my.pojo.Address;
import com.my.pojo.AddressExample;
import com.my.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImp implements AddressService {
    private AddressMapper addressMapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public long countByExample(AddressExample example) {
        return addressMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(AddressExample example) {
        return addressMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer addressId) {
        return addressMapper.deleteByPrimaryKey(addressId);
    }

    @Override
    public int insert(Address record) {
        return addressMapper.insert(record);
    }

    @Override
    public int insertSelective(Address record) {
        return addressMapper.insertSelective(record);
    }

    @Override
    public List<Address> selectByExample(AddressExample example) {
        return addressMapper.selectByExample(example);
    }

    @Override
    public Address selectByPrimaryKey(Integer addressId) {
        return addressMapper.selectByPrimaryKey(addressId);
    }

    @Override
    public int updateByExampleSelective(Address record, AddressExample example) {
        return addressMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Address record, AddressExample example) {
        return addressMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Address record) {
        return addressMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Address record) {
        return addressMapper.updateByPrimaryKey(record);
    }
}
