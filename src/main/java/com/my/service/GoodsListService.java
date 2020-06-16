package com.my.service;

import com.my.pojo.GoodsList;
import com.my.pojo.GoodsListExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GoodsListService {
    long countByExample(GoodsListExample example);

    int deleteByExample(GoodsListExample example);

    int deleteByPrimaryKey(Integer listId);

    int insert(GoodsList record);

    int insertSelective(GoodsList record);

    List<GoodsList> selectByExample(GoodsListExample example);

    GoodsList selectByPrimaryKey(Integer listId);

    int updateByExampleSelective(@Param("record") GoodsList record, @Param("example") GoodsListExample example);

    int updateByExample(@Param("record") GoodsList record, @Param("example") GoodsListExample example);

    int updateByPrimaryKeySelective(GoodsList record);

    int updateByPrimaryKey(GoodsList record);
}
