package com.zaijiadd.app.applyflow.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zaijiadd.app.applyflow.service.CommunityService;
import com.zaijiadd.app.common.utils.ContainerUtils;

@RestController
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 拉去城市下的已开小区列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listByCityName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listByCityName(@RequestBody String body) {
		try {
			JSONObject inputJson = JSONObject.parseObject(body);
			return ContainerUtils.buildResSuccessMap(this.communityService.selectByCityName(inputJson.getString("cityName")));
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
	}

}
