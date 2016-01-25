package com.zaijiadd.app.applyflow.dao;

import java.util.List;

import com.zaijiadd.app.applyflow.entity.Community;

public interface CommunityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Community record);

    int insertSelective(Community record);

    Community selectByPrimaryKey(Integer id);
    
    List<Community> selectByCityName(String cityName);

    int updateByPrimaryKeySelective(Community record);

    int updateByPrimaryKey(Community record);
}