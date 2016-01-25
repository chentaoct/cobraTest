package com.zaijiadd.app.applyflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaijiadd.app.applyflow.dao.CommunityMapper;
import com.zaijiadd.app.applyflow.entity.Community;
import com.zaijiadd.app.applyflow.service.CommunityService;

@Service
public class CommunityServiceImpl  implements CommunityService {

	@Autowired
	private CommunityMapper communityMapper;
	@Override
	public List<Community> selectByCityName(String cityName) {
		return communityMapper.selectByCityName(cityName);
	}

}
