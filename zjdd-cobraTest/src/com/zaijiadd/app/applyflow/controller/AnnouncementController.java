package com.zaijiadd.app.applyflow.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zaijiadd.app.applyflow.entity.sys.Announcement;
import com.zaijiadd.app.applyflow.service.AnnouncementService;
import com.zaijiadd.app.common.utils.ContainerUtils;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
	
	@Autowired
	private AnnouncementService announcementService;
	/**
	 * 提交公告
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submit(@RequestBody String body) {
		try {
			Announcement announcement = JSON.parseObject(body, Announcement.class);
			this.announcementService.insertSelective(announcement);
			return ContainerUtils.buildResSuccessMap(announcement);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
	}
	
	
	/**
	 * 历史公告
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String body) {
		try {
			return ContainerUtils.buildResSuccessMap(this.announcementService.select(paramParse(body)));
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
	}
	
	/**
	 * 用户公告查看情况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listByUserId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listByUserId(@RequestBody String body) {
		try {
			return ContainerUtils.buildResSuccessMap(this.announcementService.selectByUserId(paramParse(body)));
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
	}
	
	/**
	 * 更新用户的公告状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateAnnouncementStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAnnouncementStatus(@RequestBody String body) {
		try {
			this.announcementService.updateUserStatus(paramParse(body));
			return ContainerUtils.buildResSuccessMap();
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
	}
	
	/**
	 * 公告明细
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> detail(@PathVariable long id) {
		try {
			return ContainerUtils.buildResSuccessMap(this.announcementService.selectByPrimaryKey(id));
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
	}
	
	
	

	/**
	 * 查询参数封装
	 * @param jsonData
	 * @return
	 */
	private Map<String, Object> paramParse(String jsonData) {
		Map<String, Object> jsonMap = JSON.parseObject(jsonData, HashMap.class);
		String pageKey = "page";
		String countKey = "pageCount";
		//
		if (jsonMap.containsKey(pageKey)) {
			int pageCount = Integer.parseInt(jsonMap.get(countKey).toString());// 每页的数量
			int page = Integer.parseInt(jsonMap.get(pageKey).toString());
			jsonMap.remove(pageKey);
			jsonMap.remove(countKey);
			jsonMap.put("start", (page - 1) * pageCount);
			jsonMap.put("end", pageCount);
		}
		return jsonMap;
	}
	
	
}
