package com.my.dao;

import com.my.pojo.VipCard;
import com.my.pojo.VipCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VipCardMapper {
    long countByExample(VipCardExample example);

    int deleteByExample(VipCardExample example);

    int deleteByPrimaryKey(String cardId);

    int insert(VipCard record);

    int insertSelective(VipCard record);

    List<VipCard> selectByExample(VipCardExample example);

    VipCard selectByPrimaryKey(String cardId);

    int updateByExampleSelective(@Param("record") VipCard record, @Param("example") VipCardExample example);

    int updateByExample(@Param("record") VipCard record, @Param("example") VipCardExample example);

    int updateByPrimaryKeySelective(VipCard record);

    int updateByPrimaryKey(VipCard record);
}