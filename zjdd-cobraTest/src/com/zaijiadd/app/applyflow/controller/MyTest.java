/**
 * @(#)MyTest.java 2015年12月21日 Copyright 2015 it.kedacom.com, Inc. All rights
 *                 reserved.
 */

package com.zaijiadd.app.applyflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zaijiadd.app.applyflow.dao.CuserMapper;
import com.zaijiadd.app.applyflow.entity.Cuser;
import com.zaijiadd.app.common.utils.ContainerUtils;

/**
 * (用一句话描述类的主要功能)
 * @author chentao
 * @date 2015年12月21日
 */
@RequestMapping("/mytest")
@Controller
public class MyTest {

	@Autowired
	CuserMapper cuserMapper;

	/**
	 * 增加用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectUserOrderByUId(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			List<Cuser> CuserList = cuserMapper.selectUserOrderByUId(1);
			param.put("result", CuserList);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
		return param;
	}

	@RequestMapping(value = "/user1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectUserOrderByUIdMap(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			List<Cuser> CuserList = cuserMapper.selectUserOrderByUIdMap(1);
			param.put("result", CuserList);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
		return param;
	}
}
