package com.zaijiadd.app.applyflow.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zaijiadd.app.applyflow.service.SystemService;
import com.zaijiadd.app.common.utils.ContainerUtils;

/**
 * 一些系统级的特殊接口
 * @author Gary Guo
 *
 */
@RestController
public class SystemController {
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 帮助中心信息获取
	 * @return
	 */
	@RequestMapping(value = "/helpCenter", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> helpCenter() {
		try {
			return ContainerUtils.buildResSuccessMap(systemService.helpCenter());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
