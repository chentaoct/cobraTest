/**
 * @(#)InviteUserController.java 2015年11月28日 Copyright 2015 it.kedacom.com, Inc.
 *                               All rights reserved.
 */

package com.zaijiadd.app.applyflow.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zaijiadd.app.applyflow.entity.ApplyStore;
import com.zaijiadd.app.applyflow.entity.InviteUserEntity;
import com.zaijiadd.app.applyflow.service.ApplyFlowService;
import com.zaijiadd.app.applyflow.service.AreaService;
import com.zaijiadd.app.common.utils.ContainerUtils;
import com.zaijiadd.app.common.utils.DateUtils;
import com.zaijiadd.app.common.utils.ParseUtils;
import com.zaijiadd.app.exception.BusinessException;
import com.zaijiadd.app.user.entity.UserInfoEntity;
import com.zaijiadd.app.utils.constants.ConstantStorePower;
import com.zaijiadd.app.utils.constants.ConstantsRole;

/**
 * 流程申请
 * @author chentao
 * @date 2015年11月28日
 */
@RequestMapping("/applyFlow")
@Controller
public class ApplyFlowController {

	private final static Logger logger = LoggerFactory.getLogger(ApplyFlowController.class);
	@Autowired
	private ApplyFlowService applyFlowService;
	@Autowired
	AreaService areaService;

	/**
	 * 增加用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addInviteUserMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addInviteUserMsg(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			InviteUserEntity inviteUserEntity = jsonToInviteUserVO(jsonRequest);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息

			Integer userId = user.getUserId();
			inviteUserEntity.setYjsUserId(userId);
			inviteUserEntity.setFuctionSate(1);
			Integer inviteUserId = applyFlowService.addInviteUser(inviteUserEntity);

			inviteUserId = inviteUserEntity.getInviteUserid();
			param.put("inviteUserId", inviteUserId);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}
	}

	/**
	 * userId roleId
	 * @param request
	 * @param jsonRequest
	 * @return
	 */

	private UserInfoEntity getUserMsg(HttpServletRequest request, JSONObject jsonRequest) {
		UserInfoEntity user = new UserInfoEntity();
		Integer userId = jsonRequest.getInteger("userId");
		Integer roleId = jsonRequest.getInteger("roleId");
		user.setRoleId(roleId);
		user.setUserId(userId);
		return user;
	}

	/**
	 * 增加邀约记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addInviteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addInviteUser(HttpServletRequest request) {
		try {
			//String parameter = request.getParameter("inviteuserName");
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			InviteUserEntity inviteUserEntity = jsonToInviteUserVO(jsonRequest);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息

			Integer userId = user.getUserId();
			inviteUserEntity.setYjsUserId(userId);
			inviteUserEntity.setFuctionSate(2);// 邀约
			inviteUserEntity.setUserAddFlag(0);// 用户标记默认为0

			Integer inviteUserId = applyFlowService.addInviteUser(inviteUserEntity);
			inviteUserId = inviteUserEntity.getInviteUserid();
			param.put("inviteUserId", inviteUserId);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 根据用户id和姓名、电话查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryInviteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInviteUser(HttpServletRequest request) {
		JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("inviteuserName", jsonRequest.getString("inviteuserName"));
		param.put("inviteuserMobile", jsonRequest.getString("inviteuserMobile"));
		param.put("yjsUserId", jsonRequest.getString("yjsUserId"));
		InviteUserEntity inviteUserEntity = applyFlowService.queryInviteUser(param);
		Map<String, Object> entityToMap = ContainerUtils.entityToMap(inviteUserEntity);
		return ContainerUtils.buildResSuccessMap(entityToMap);

	}

	/**
	 * 查询用户信息详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryInviteUserMsgById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInviteUserMsgById(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("inviteUserid", jsonRequest.getString("inviteUserid"));
			param.put("fuctionSate", "1");
			Map<String, Object> inviteUserEntityDet = applyFlowService.queryInviteUserMsgDet(param);

			if (inviteUserEntityDet != null) {
				Date createDateTime = (Date) inviteUserEntityDet.get("createDate");
				String sysDateStr = DateUtils.getSysDate(createDateTime, "yyyy-MM-dd HH:mm:ss");
				inviteUserEntityDet.put("createDate", sysDateStr);
			}
			param.put("result", inviteUserEntityDet);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}
	/**
	 * 查询用户信息详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryInviteUserMsgById2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInviteUserMsgById2(HttpServletRequest request) {
		try {
			String parameter = request.getParameter("inviteUserid");
			System.out.println(parameter);
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("inviteUserid", jsonRequest.getString("inviteUserid"));
			param.put("fuctionSate", "1");
			Map<String, Object> inviteUserEntityDet = applyFlowService.queryInviteUserMsgDet(param);

			if (inviteUserEntityDet != null) {
				Date createDateTime = (Date) inviteUserEntityDet.get("createDate");
				String sysDateStr = DateUtils.getSysDate(createDateTime, "yyyy-MM-dd HH:mm:ss");
				inviteUserEntityDet.put("createDate", sysDateStr);
			}
			param.put("result", inviteUserEntityDet);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}
	/**
	 * 查询邀约记录详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryInviteUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInviteUserById(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("inviteUserid", jsonRequest.getString("inviteUserid"));
			param.put("fuctionSate", "2");
			Map<String, Object> inviteUserEntityDet = applyFlowService.queryInviteUserDet(param);
			if (inviteUserEntityDet != null) {
				Date visitTime = (Date) inviteUserEntityDet.get("visitTime");
				String sysDateStr = DateUtils.getSysDate(visitTime, "yyyy-MM-dd");
				inviteUserEntityDet.put("visitTime", sysDateStr);
			}

			param.put("result", inviteUserEntityDet);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 查询所有用户信息,分页map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryAllInviteUserMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllInviteUserMsg(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer userId = user.getUserId();

			Map<String, Object> param = new HashMap<String, Object>();
			Integer userState = jsonRequest.getInteger("userState");// 邀约状态
			String page = jsonRequest.getString("page");// 当前页
			Integer pageCount = jsonRequest.getInteger("pageCount");// 每页的数量
			param.put("start", (Integer.parseInt(page) - 1) * pageCount);// 从那里开始
			param.put("end", pageCount);

			param.put("yjsUserId", userId);
			param.put("fuctionSate", "1");
			param.put("userState", userState);

			List<Map<String, Object>> inviteUserMap = applyFlowService.queryAllInviteUserMsg(param);
			for (Map<String, Object> map : inviteUserMap) {
				Date createDate = (Date) map.get("createDate");
				map.put("createDate", DateUtils.getSysDate(createDate, "yyyy-MM-dd"));
			}
			Integer dataCount = applyFlowService.queryByParamInviteUserCount(param);
			param.put("dataCount", dataCount);
			param.put("result", inviteUserMap);
			// String visitTime = inviteUserEntity.getVisitTime();
			// inviteUserEntity.setVisitTime(DateUtils.getDate(visitTime));
			// Map<String, Object> entityToMap =
			// ContainerUtils.entityToMap(inviteUserEntity);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 查询所有邀约,分页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryAllInviteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllInviteUser(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer userId = user.getUserId();
			Map<String, Object> param = new HashMap<String, Object>();
			String page = jsonRequest.getString("page");// 当前页
			Integer pageCount = jsonRequest.getInteger("pageCount");// 每页的数量
			Integer userState = jsonRequest.getInteger("userState");// 邀约状态
			param.put("start", (Integer.parseInt(page) - 1) * pageCount);// 从那里开始
			param.put("end", pageCount);
			param.put("yjsUserId", userId);
			param.put("fuctionSate", "2");
			param.put("userState", userState);
			List<Map<String, Object>> inviteUserMap = applyFlowService.queryInviteUserMap(param);
			for (Map<String, Object> map : inviteUserMap) {
				Date createDate = (Date) map.get("createDate");
				map.put("createDate", DateUtils.getSysDate(createDate, "yyyy-MM-dd"));
				Date visitTime = (Date) map.get("visitTime");
				map.put("visitTime", DateUtils.getSysDate(visitTime, "yyyy-MM-dd"));

			}
			Integer dataCount = applyFlowService.queryByParamInviteUserCount(param);
			param.put("dataCount", dataCount);
			param.put("result", inviteUserMap);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 用户信息模糊查询,分页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryInviteUserMsgLike", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInviteUserMsgLike(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer userId = user.getUserId();

			Map<String, Object> param = new HashMap<String, Object>();
			String page = jsonRequest.getString("page");// 当前页
			Integer pageCount = jsonRequest.getInteger("pageCount");// 每页的数量
			param.put("start", (Integer.parseInt(page) - 1) * pageCount);// 从那里开始
			param.put("end", pageCount);
			param.put("yjsUserId", userId);
			param.put("inviteuserName", jsonRequest.getString("inviteuserName"));
			param.put("fuctionSate", "1");
			List<Map<String, Object>> inviteUseMap = applyFlowService.queryInviteUserMsgLike(param);
			for (Map<String, Object> map : inviteUseMap) {
				Date createDate = (Date) map.get("createDate");
				map.put("createDate", DateUtils.getSysDate(createDate, "yyyy-MM-dd"));
			}
			Integer dataCount = applyFlowService.queryByParamInviteUserCountLike(param);
			param.put("dataCount", dataCount);
			param.put("result", inviteUseMap);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 邀约模糊查询,分页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryInviteUserLike", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInviteUserLike(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer userId = user.getUserId();

			Map<String, Object> param = new HashMap<String, Object>();
			String page = jsonRequest.getString("page");// 当前页
			Integer pageCount = jsonRequest.getInteger("pageCount");// 每页的数量
			param.put("start", (Integer.parseInt(page) - 1) * pageCount);// 从那里开始
			param.put("end", pageCount);
			param.put("yjsUserId", userId);
			param.put("inviteuserName", jsonRequest.getString("inviteuserName"));
			param.put("fuctionSate", "2");
			List<Map<String, Object>> inviteUseMap = applyFlowService.queryInviteUserLike(param);
			for (Map<String, Object> map : inviteUseMap) {
				Date createDate = (Date) map.get("createDate");
				map.put("createDate", DateUtils.getSysDate(createDate, "yyyy-MM-dd"));
				Date visitTime = (Date) map.get("visitTime");
				map.put("visitTime", DateUtils.getSysDate(visitTime, "yyyy-MM-dd"));
			}
			Integer dataCount = applyFlowService.queryByParamInviteUserCountLike(param);
			param.put("dataCount", dataCount);
			param.put("result", inviteUseMap);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 更新
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateInviteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateInviteUser(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer userId = user.getUserId();
			Map<String, Object> param = new HashMap<String, Object>();
			InviteUserEntity inviteUserEntity = jsonToInviteUserVO(jsonRequest);
			inviteUserEntity.setYjsUserId(userId);
			Integer inviteUserId = applyFlowService.updateInviteUser(inviteUserEntity);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 更新用户的标记
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateUserAddFlagById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserAddFlagById(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			Integer inviteUserid = jsonRequest.getInteger("inviteUserid");
			Integer userAddFlag = jsonRequest.getInteger("userAddFlag");
			param.put("inviteUserid", inviteUserid);
			param.put("userAddFlag", userAddFlag);
			Integer inviteUserId = applyFlowService.updateUserAddFlagById(param);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 邀约记录状态更改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateInviteUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateInviteUserById(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			Integer inviteUserid = jsonRequest.getInteger("inviteUserid");
			Integer userState = jsonRequest.getInteger("userState");
			param.put("inviteUserid", inviteUserid);
			param.put("userState", userState);
			Integer inviteUserId = applyFlowService.updateInviteUserById(param);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param jsonRequest
	 * @return
	 */

	private InviteUserEntity jsonToInviteUserVO(JSONObject jsonRequest) {
		InviteUserEntity inviteUserEntity = jsonRequest.parseObject(jsonRequest.toJSONString(), InviteUserEntity.class);
		return inviteUserEntity;
	}

	/**
	 * 增加开店申请单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addApplyStore(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Map<String, Object> param = new HashMap<String, Object>();
			ApplyStore applyStore = jsonToaddApplyStore(jsonRequest);
			applyStore.setApplyStatus(ConstantStorePower.apply_state_ready);// 待申请状态
			applyStore.setWhetherStartApply(ConstantStorePower.WHETHER_STARTAPPLY_NO);// 没有发起收款申请
			applyStore.setFinanceCheck(ConstantStorePower.approve_state_ready);
			applyStore.setManagersCheck(ConstantStorePower.approve_state_ready);
			Integer userId = user.getUserId();

			applyStore.setYjsUserId(userId);
			String possNum = applyFlowService.addApplyStore(applyStore, param);
			param.put("possNum", possNum);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (BusinessException e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMapMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 查询用户开店权限详细信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryApplyStoreDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryApplyStoreDetails(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息

			Integer applyStoreId = jsonRequest.getInteger("applyStoreId");
			Map<String, Object> applyStoreMap = applyFlowService.queryApplyStoreDetails(applyStoreId);

			// 这里面状态是给页面用的---与数据库含义不同
			Integer roleApprove = (java.lang.Integer) applyStoreMap.get("roleApprove");
			Integer whoCheck = (java.lang.Integer) applyStoreMap.get("whoCheck");// 数据库中的是userId
			if (whoCheck != null) {
				UserInfoEntity userInfoEntity = applyFlowService.getUserInfoById(whoCheck);
				if (userInfoEntity != null) {
					Integer roleId = userInfoEntity.getRoleId();
					applyStoreMap.put("whoCheck", roleId);
				}

			}

			if (roleApprove != null) {
				applyStoreMap.put("whoCheck", roleApprove);
			}

			applyStoreMapDateToString(applyStoreMap);
			param.put("result", applyStoreMap);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 时间转换
	 * @param applyStoreMap
	 */

	void applyStoreMapDateToString(Map<String, Object> applyStoreMap) {
		if (applyStoreMap != null) {
			Date createDate = (Date) applyStoreMap.get("createdDate");
			String createDateStr = DateUtils.getSysDate(createDate, "yyyy-MM-dd");
			applyStoreMap.put("createdDate", createDateStr);
		}

	}

	/**
	 * 查询订单状态--总
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryAllApplyStoreSate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllApplyStoreSate(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Map<String, Object> param = new HashMap<String, Object>();
			String page = jsonRequest.getString("page");// 当前页
			Integer pageCount = jsonRequest.getInteger("pageCount");// 每页的数量
			param.put("start", (Integer.parseInt(page) - 1) * pageCount);// 从那里开始
			param.put("end", pageCount);

			Integer applyStatus = jsonRequest.getInteger("applyStatus");// 订单状态
			Integer userId = user.getUserId();
			param.put("yjsUserId", userId);
			param.put("applyStatus", applyStatus);
			List<Map<String, Object>> applyStoreOrderMap = new ArrayList<Map<String, Object>>();
			if (ConstantStorePower.apply_state_ready.equals(applyStatus)) {// 申请中的
				applyStoreOrderMap = applyFlowService.queryAllApplyStoreSateIn(param);
			} else if (ConstantStorePower.apply_state_succ.equals(applyStatus)) {// 申请成功的
				applyStoreOrderMap = applyFlowService.queryAllApplyStoreSate(param);
			}
			mapListValueToDate(applyStoreOrderMap);

			Integer dataCount = applyFlowService.queryByParamCount(param);
			param.put("dataCount", dataCount);
			param.put("result", applyStoreOrderMap);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * createDate
	 * @param applyStoreOrderMap
	 */

	private void mapListValueToDate(List<Map<String, Object>> applyStoreOrderMap) {
		for (Map<String, Object> map : applyStoreOrderMap) {
			applyStoreMapDateToString(map);
		}
	}

	/**
	 * 查询角色需要审批
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryRoleApproveStoreTry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRoleApproveStoreTry(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			String page = jsonRequest.getString("page");// 当前页
			Integer pageCount = jsonRequest.getInteger("pageCount");// 每页的数量
			param.put("start", (Integer.parseInt(page) - 1) * pageCount);// 从那里开始
			param.put("end", pageCount);
			Integer userId = jsonRequest.getInteger("userId");
			UserInfoEntity userInfoEntity = applyFlowService.getUserInfoById(userId);
			Integer roleId = userInfoEntity.getRoleId();

			if (ConstantsRole.ROLE_MANAGERS.equals(roleId)) {// 经理
				param.put("whoCheck", userInfoEntity.getUserId());

			} else if (ConstantsRole.ROLE_FINANCE.equals(roleId)) {
				param.put("roleApprove", userInfoEntity.getRoleId());
				param.put("whetherStartApply", ConstantStorePower.WHETHER_STARTAPPLY_YES);
			}

			param.put("applyStatus", ConstantStorePower.apply_state_ready);

			List<Map<String, Object>> applyStoreMap = applyFlowService.queryRoleApproveStoreTry(param);
			mapListValueToDate(applyStoreMap);
			Integer dataCount = applyFlowService.queryByParamCount(param);
			param.put("dataCount", dataCount);
			param.put("result", applyStoreMap);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 审批(财务,主管)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approveStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approveStore(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			Integer applyStoreId = jsonRequest.getInteger("applyStoreId");
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer userId = user.getUserId();
			Integer roleId = user.getRoleId();
			param.put("roleId", roleId);
			param.put("userId", userId);

			param.put("applyStoreId", applyStoreId);
			param.put("approveState", jsonRequest.getInteger("approveState"));// 状态
			Integer applyStoreMap = applyFlowService.roleApproveStore(param);
			param.put("result", applyStoreMap);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 查询审批过的信息(主管、财务)---
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryApproveMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllApplyStore(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			String page = jsonRequest.getString("page");// 当前页
			Integer pageCount = jsonRequest.getInteger("pageCount");// 每页的数量
			param.put("start", (Integer.parseInt(page) - 1) * pageCount);// 从那里开始
			param.put("end", pageCount);

			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer roleId = user.getRoleId();
			Integer userId = user.getUserId();

			param.put("roleId", roleId);
			param.put("userId", userId);
			List<Map<String, Object>> applyStoreMap = applyFlowService.queryApproveMsg(param);
			for (Map<String, Object> map : applyStoreMap) {
				Date createDate = (Date) map.get("createdDate");
				String createDateStr = DateUtils.getSysDate(createDate, "yyyy-MM-dd");
				map.put("createdDate", createDateStr);
			}
			Integer dataCount = applyFlowService.queryApproveMsgCount(param);
			param.put("result", applyStoreMap);
			param.put("dataCount", dataCount);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 查询银行信息--列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryBankList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBankList(HttpServletRequest request) {
		JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> bankList = applyFlowService.queryBankList(param);
		param.put("result", bankList);
		return ContainerUtils.buildResSuccessMap(param);
	}

	/**
	 * 查询城市可以买的经销权个数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDealershipNumAble", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDealershipNumAble(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			Integer cityId = jsonRequest.getInteger("cityId");
			Integer districtId = jsonRequest.getInteger("districtId");
			// Integer applyStatus = jsonRequest.getInteger("applyStatus");
			param.put("cityId", cityId);
			param.put("districtId", districtId);
			// param.put("applyStatus", ConstantStorePower.apply_state_succ);

			Map<String, Object> findCitySellInfo = areaService.findCitySellInfo(cityId, districtId);
			BigDecimal money = (BigDecimal) findCitySellInfo.get("money");
			Integer laveNum = (Integer) findCitySellInfo.get("laveNum");// 可以用的
			Integer totalDealership = (Integer) findCitySellInfo.get("total");// 总共的
			// 查询提交了的申请单,待申请中的,是0的才减，其它的不用减
			List<Map<String, Object>> applyStoreOrderMap = applyFlowService.queryApplyDealershipNum(param);
			for (Map<String, Object> map : applyStoreOrderMap) {
				Integer dealershipNum = (Integer) map.get("dealershipNum");
				if (laveNum != null && dealershipNum != null) {
					laveNum = laveNum - dealershipNum;
				}
				if (laveNum < 0) {
					laveNum = 0;
					break;
				}

			}
			param.put("dealershipAble", laveNum);
			param.put("totalDealership", totalDealership);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 更新开店申请
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateApplyStore(HttpServletRequest request) {

		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			ApplyStore applyStore = jsonToaddApplyStore(jsonRequest);

			ApplyStore applyStore2 = applyFlowService.selectByAppStoreId(applyStore.getApplyStoreId());// 老的记录
			Integer applyStatus = applyStore2.getApplyStatus();
			Integer managersCheck = applyStore2.getManagersCheck();
			if (ConstantStorePower.apply_state_fail.equals(applyStatus)
					&& (ConstantStorePower.apply_state_fail.equals(managersCheck))) {// 被主管拒绝
				String possNum = applyFlowService.handleUpdate(applyStore);
				param.put("possNum", possNum);

			} else {
				Integer applyStoreId = applyFlowService.updateApplyStore(applyStore);
			}

			return ContainerUtils.buildResSuccessMap(param);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 尚未处理异常
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 支付尾款
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/payRemainMoney", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> payRemainMoney(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			ApplyStore applyStore = jsonToaddApplyStore(jsonRequest);
			// UserInfoEntity user = getUserMsg(request, jsonRequest);
			// Integer roleId = user.getRoleId();
			// Integer userId = user.getUserId();
			// applyStore.setYjsUserId(userId);

			String possNum = applyFlowService.payRemainMoney(applyStore);
			param.put("possNum", possNum);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 更新是否发起申请
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateWhetherStartApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWhetherStartApply(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			ApplyStore applyStore = jsonToaddApplyStore(jsonRequest);
			applyStore.setRoleApprove(ConstantsRole.ROLE_FINANCE);
			Integer applyStoreId = applyFlowService.updateWhetherStartApply(applyStore);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * 生成财务流水号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/generateSeriaFinancelNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateSerialNum(HttpServletRequest request) {
		JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
		Map<String, Object> param = new HashMap<String, Object>();

		String generateSerialNum = applyFlowService.generateSerialNum();
		param.put("financelNum", generateSerialNum);
		return ContainerUtils.buildResSuccessMap(param);
	}

	/**
	 * 打印合同
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/printContract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> printContract(HttpServletRequest request) {
		try {
			JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
			Map<String, Object> param = new HashMap<String, Object>();
			Integer applyStoreId = jsonRequest.getInteger("applyStoreId");
			UserInfoEntity user = getUserMsg(request, jsonRequest);// 用户信息
			Integer userId = user.getUserId();
			param.put("applyStoreId", applyStoreId);
			param.put("userId", userId);
			Map<String, Object> printApply = applyFlowService.printContract(param);
			if (printApply != null) {
				BigDecimal paidMoney = (BigDecimal) printApply.get("paidMoney");
				BigDecimal needPaymoney = (BigDecimal) printApply.get("needPaymoney");
				BigDecimal allMoney = needPaymoney.add(paidMoney);
				printApply.put("allMoney", allMoney);
				String contractDate = applyFlowService.getFinanceApproveSucce(applyStoreId, userId);// 查询财务同意的
				String contractNum = applyFlowService.generateContractNum(applyStoreId, contractDate);
				contractNum = handleContractNum(contractDate, contractNum);
				printApply.put("contractNum", contractNum);// 合同编号
				printApply.put("contractDate", contractDate);// 合同日期
				// Integer applyType = (Integer) printApply.get("applyType");//
				// 申请类型
				// Map<String, Object> applyContractParam = new HashMap<String,
				// Object>();
				// applyContractParam.put("applyContractType", applyType);
				// ApplyContract applyContract =
				// applyFlowService.getApplyContract(applyContractParam);
				// param.put("applyContractContent",
				// applyContract.getApplyContractContent());
			}

			param.put("result", printApply);
			return ContainerUtils.buildResSuccessMap(param);
		} catch (Exception e) {
			e.printStackTrace();
			return ContainerUtils.buildResFailMap();
		}

	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param contractDate
	 * @param contractNum
	 * @return
	 */

	private String handleContractNum(String contractDate, String contractNum) {
		if (contractNum.length() <= 4) {
			int zeroNum = 4 - contractNum.length();
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < zeroNum; i++) {
				stringBuilder.append(0);
			}
			stringBuilder.append(contractNum);
			return contractDate.replaceAll("-", "") + "" + stringBuilder.toString();
		} else {
			return contractDate.replaceAll("-", "") + "" + contractNum;
		}

	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param jsonRequest
	 * @return
	 */

	private ApplyStore jsonToaddApplyStore(JSONObject jsonRequest) {
		ApplyStore applyStore = jsonRequest.parseObject(jsonRequest.toJSONString(), ApplyStore.class);
		return applyStore;
	}

	/**
	 * 测试
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tests", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> test1(HttpServletRequest request) {
		JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
		Map<String, Object> param = new HashMap<String, Object>();
		applyFlowService.cleanLoseEfficacyApplyStore();
		String contextPath = request.getContextPath();
		return ContainerUtils.buildResSuccessMap(param);
	}

	/**
	 * 测试
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tests2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tests2(HttpServletRequest request) {
		JSONObject jsonRequest = ParseUtils.loadJsonPostRequest(request);
		// InviteUserEntity inviteUserEntity = new InviteUserEntity();
		// inviteUserEntity.setInviteUserid(1);
		// inviteUserEntity.setInviteuserName("aaa");
		// List arrayList = new ArrayList<InviteUserEntity>();
		// arrayList.add(inviteUserEntity);
		// arrayList.add(inviteUserEntity);
		// Student student = new Student();
		// student.setInviteUserEntity(arrayList);
		return ContainerUtils.buildResSuccessMap();
	}
}
