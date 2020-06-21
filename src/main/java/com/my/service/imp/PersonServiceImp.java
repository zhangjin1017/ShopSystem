package com.my.service.imp;

import com.my.dao.PersonMapper;
import com.my.pojo.Person;
import com.my.pojo.PersonExample;
import com.my.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImp implements PersonService {

    private PersonMapper personMapper;

    @Autowired
    public void setPersonMapper(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }


    @Override
    public long countByExample(PersonExample example) {
        return personMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PersonExample example) {
        return personMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer personId) {
        return personMapper.deleteByPrimaryKey(personId);
    }

    @Override
    public int insert(Person record) {
        return personMapper.insert(record);
    }

    @Override
    public int insertSelective(Person record) {
        return personMapper.insertSelective(record);
    }

    @Override
    public List<Person> selectByExample(PersonExample example) {
        System.out.println(example);
        return personMapper.selectByExample(example);
    }

    @Override
    public Person selectByPrimaryKey(Integer personId) {
        return personMapper.selectByPrimaryKey(personId);
    }

    @Override
    public int updateByExampleSelective(Person record, PersonExample example) {
        return personMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Person record, PersonExample example) {
        return personMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Person record) {
        return personMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Person record) {
        return personMapper.updateByPrimaryKey(record);
    }
}
