package com.zaijiadd.app.applyflow.service;

import java.util.List;

import com.zaijiadd.app.applyflow.entity.Community;

/**
 * 社区管理
 * @author Gary Guo
 *
 */
public interface CommunityService {
	
	List<Community> selectByCityName(String cityName);

}
