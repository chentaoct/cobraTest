/**
 * @(#)AppPersonFlowImpl.java 2015年11月28日 Copyright 2015 it.kedacom.com, Inc.
 *                            All rights reserved.
 */

package com.zaijiadd.app.applyflow.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaijiadd.app.applyflow.dao.ApplyContractMapper;
import com.zaijiadd.app.applyflow.dao.ApplyFlowDao;
import com.zaijiadd.app.applyflow.dao.ApplyStoreDao;
import com.zaijiadd.app.applyflow.dao.ApplyStoreDetailDao;
import com.zaijiadd.app.applyflow.dao.ApplyUserRelationDao;
import com.zaijiadd.app.applyflow.dao.BankMapper;
import com.zaijiadd.app.applyflow.dao.CityDealershipMapper;
import com.zaijiadd.app.applyflow.dao.CityMapper;
import com.zaijiadd.app.applyflow.dao.ContractNumMapper;
import com.zaijiadd.app.applyflow.dao.CountryMapper;
import com.zaijiadd.app.applyflow.dao.SerialNumberMapper;
import com.zaijiadd.app.applyflow.entity.ApplyContract;
import com.zaijiadd.app.applyflow.entity.ApplyStore;
import com.zaijiadd.app.applyflow.entity.ApplyStoreDetail;
import com.zaijiadd.app.applyflow.entity.ApplyUserRelation;
import com.zaijiadd.app.applyflow.entity.City;
import com.zaijiadd.app.applyflow.entity.ContractNum;
import com.zaijiadd.app.applyflow.entity.Country;
import com.zaijiadd.app.applyflow.entity.InviteUserEntity;
import com.zaijiadd.app.applyflow.entity.SerialNumber;
import com.zaijiadd.app.applyflow.service.ApplyFlowService;
import com.zaijiadd.app.applyflow.service.AreaService;
import com.zaijiadd.app.common.utils.DateUtils;
import com.zaijiadd.app.exception.BusinessException;
import com.zaijiadd.app.system.service.SystemUserService;
import com.zaijiadd.app.user.dao.UserInfoDAO;
import com.zaijiadd.app.user.entity.UserInfoEntity;
import com.zaijiadd.app.utils.constants.ConstantStorePower;
import com.zaijiadd.app.utils.constants.ConstantsRole;

/**
 * (用一句话描述类的主要功能)
 * @author chentao
 * @date 2015年11月28日
 */
@Service
@Transactional
public class ApplyFlowServiceImpl implements ApplyFlowService {

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#addInviteUser(com.zaijiadd.app.applyflow.entity.InviteUserEntity)
	 */
	private final static Logger logger = LoggerFactory.getLogger(ApplyFlowServiceImpl.class);
	@Autowired
	ApplyFlowDao applyFlowDao;
	@Autowired
	ContractNumMapper contractNumMapper;
	@Autowired
	SerialNumberMapper serialNumberMapper;
	@Autowired
	ApplyStoreDao applyStoreDao;
	@Autowired
	ApplyStoreDetailDao applyStoreDetailDao;

	@Autowired
	BankMapper bankMapper;
	@Autowired
	CityDealershipMapper cityDealershipMapper;

	@Autowired
	AreaService areaService;
	@Autowired
	ApplyUserRelationDao applyUserRelationDao;
	@Autowired
	private UserInfoDAO userInfoDao;
	@Autowired
	private CityMapper cityMapper;
	@Autowired
	private CountryMapper countryMapper;
	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private ApplyContractMapper applyContractMapper;

	@Override
	public Integer addInviteUser(InviteUserEntity inviteUserEntity) {
		return applyFlowDao.addInviteUser(inviteUserEntity);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#updateInviteUser(com.zaijiadd.app.applyflow.entity.InviteUserEntity)
	 */

	@Override
	public Integer updateInviteUser(InviteUserEntity inviteUserEntity) {
		applyFlowDao.updateInviteUser(inviteUserEntity);
		return null;
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryInviteUser(java.util.Map)
	 */

	@Override
	public InviteUserEntity queryInviteUser(Map<String, Object> param) {
		return applyFlowDao.queryInviteUser(param);
	}

	/**
	 * @throws Exception
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#addApplyStore(com.zaijiadd.app.applyflow.entity.ApplyStore,
	 *      Map)
	 */

	@Override
	public String addApplyStore(ApplyStore applyStore, Map<String, Object> param) throws Exception {

		Integer applyType = applyStore.getApplyType();// 申请类型
		Integer paymoneyType = applyStore.getPaymoneyType();// 付款类型
		whoCheck(applyStore, applyType, paymoneyType);
		Integer applyStoreId = applyStoreDao.addApplyStore(applyStore);
		Integer returnApplyStoreId = applyStore.getApplyStoreId();
		param.put("applyStoreId", returnApplyStoreId);

		String possNum = null;
		if (ConstantStorePower.APPLY_PAY_WAY_SWIPING_CARD.equals(applyStore.getPayWay())) {// 刷卡
			possNum = generateSerialNum();// 生成流水号
			ApplyStore applyStore2 = new ApplyStore();
			applyStore2.setPossNum(possNum);
			applyStore2.setApplyStoreId(applyStore.getApplyStoreId());
			updateApplyStore(applyStore2);
			return possNum;

		}
		return possNum;
	}

	/**
	 * 谁审核
	 * @param applyStore
	 * @param applyType
	 * @param paymoneyType
	 * @throws Exception
	 */

	void whoCheck(ApplyStore applyStore, Integer applyType, Integer paymoneyType) throws Exception {

		if (ConstantStorePower.APPLY_PAYMONEY_NOTALL.equals(paymoneyType)) {// 定金直接给财务
			applyStore.setRoleApprove(ConstantsRole.ROLE_FINANCE);
		} else {// 全额
			if (applyType.equals(ConstantStorePower.APPLY_TYPE_DEALERSHIP)) {// 经销权

				BigDecimal personPaymoneyCount = getPersonPaymoneyCount(applyStore);
				// 经销权价格计算
				BigDecimal needPaymoneyCount = getDealershipMoney(applyStore);

				if (personPaymoneyCount.compareTo(needPaymoneyCount) < 0) {// 没有支付全额
					UserInfoEntity leader = systemUserService.getLeader(applyStore.getYjsUserId());
					// 实际付的金额比应收的金额小，那么给经理审批
					applyStore.setWhoCheck(leader.getUserId());
					applyStore.setWhetherStartApply(ConstantStorePower.WHETHER_STARTAPPLY_NO);//
					// 没有发起收款申请
				} else {// 实际付的金额比应收的金额 相等，给财务
					applyStore.setRoleApprove(ConstantsRole.ROLE_FINANCE);
				}
			} else if (applyType.equals(ConstantStorePower.APPLY_TYPE_SMALLSTORE)) {// 小店

				BigDecimal personPaymoneyCount = getPersonPaymoneyCount(applyStore);
				// 小店价格计算
				BigDecimal needPaymoneyCount = getNeedPaymoneyCount(applyStore);

				if (personPaymoneyCount.compareTo(needPaymoneyCount) < 0) {// 没有支付全额
					// UserInfoEntity leader =
					// systemUserService.getLeader(applyStore.getYjsUserId());
					// // 实际付的金额比应收的金额小，那么给主管审批
					// applyStore.setWhoCheck(leader.getUserId());
					// applyStore.setWhetherStartApply(ConstantStorePower.WHETHER_STARTAPPLY_NO);//
					// 没有发起收款申请
					logger.debug("小店没有支付全额");
				} else {// 实际付的金额比应收的金额 相等，给财务
					applyStore.setRoleApprove(ConstantsRole.ROLE_FINANCE);
				}
			}
		}
	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param applyStore
	 * @return
	 * @throws Exception
	 */

	BigDecimal getDealershipMoney(ApplyStore applyStore) throws Exception {
		Integer dealershipNum = applyStore.getDealershipNum();// 经销权个数
		BigDecimal dealershipNumBig = new BigDecimal(dealershipNum);
		BigDecimal cityDealershipMoney = new BigDecimal(1);
		Map<String, Object> findCitySellInfo = areaService.findCitySellInfo(applyStore.getCityId(),
				applyStore.getDistrictId());

		BigDecimal needPaymoneyCount = new BigDecimal(0);
		if (findCitySellInfo != null) {
			cityDealershipMoney = (BigDecimal) findCitySellInfo.get("money");
			// cityDealershipMoney = new BigDecimal(money);// 钱
			Integer dealershipNumAble = (Integer) findCitySellInfo.get("laveNum");// 总个数
			if (dealershipNumAble == null) {
				dealershipNumAble = 0;
			}
			if (dealershipNumAble - dealershipNum < 0) {
				throw new BusinessException("超过经销权个数");
			} else {
				if (dealershipNumBig.compareTo(BigDecimal.ZERO) == 0) {
					needPaymoneyCount = cityDealershipMoney;
				} else {
					needPaymoneyCount = cityDealershipMoney.multiply(dealershipNumBig);// 每个城市的价格X个数，需支付的
				}
			}

		}

		return needPaymoneyCount;
	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param applyStore
	 * @return
	 */

	BigDecimal getNeedPaymoneyCount(ApplyStore applyStore) {
		BigDecimal needPaymoneyCount = new BigDecimal(0);
		Integer storeNumm = applyStore.getStoreNumm();
		if (storeNumm != null) {
			if (storeNumm == 0) {
				storeNumm = 1;
			}
			BigDecimal storeNummBigDecimal = new BigDecimal(storeNumm);
			needPaymoneyCount = storeNummBigDecimal.multiply(ConstantStorePower.STORE_MONEY);// 每个城市的价格X个数，需支付的
		}
		return needPaymoneyCount;
	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param applyStore
	 * @return
	 */

	BigDecimal getPersonPaymoneyCount(ApplyStore applyStore) {
		Integer cityId = applyStore.getCityId();
		BigDecimal paidMoney = applyStore.getPaidMoney();// 已付金额
		BigDecimal needPaymoney = applyStore.getNeedPaymoney();// 应付金额
		BigDecimal personPaymoneyCount = paidMoney.add(needPaymoney);// 个人现在已经付的金额
		return personPaymoneyCount;
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryAllApplyStore(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryAllApplyStore(Map<String, Object> param) {
		return applyFlowDao.queryAllApplyStore(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryInviteUserLike(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryInviteUserLike(Map<String, Object> param) {
		return applyFlowDao.queryInviteUserLike(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryInviteUserMap(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryInviteUserMap(Map<String, Object> param) {
		return applyFlowDao.queryInviteUserMap(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#updateApplyStore(com.zaijiadd.app.applyflow.entity.ApplyStore)
	 */

	@Override
	public Integer updateApplyStore(ApplyStore applyStore) {
		return applyStoreDao.updateApplyStore(applyStore);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryApplyStoreDetails(Integer)
	 */

	@Override
	public Map<String, Object> queryApplyStoreDetails(Integer applyStoreId) {
		return applyStoreDao.queryApplyStoreDetails(applyStoreId);
	}

	/**
	 * @return
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#getCityDealershipMoney(Integer)
	 */

	@Override
	public BigDecimal getCityDealershipMoney(Integer cityId) {
		ArrayList<City> city = cityMapper.selectCityByID(cityId);
		if (city.size() > 0) {
			return city.get(0).getCityMoney();

		}
		return new BigDecimal(1);

	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param applyUserRelation
	 */

	private void insertApplyRoleRelation(ApplyUserRelation applyUserRelation) {
		applyUserRelationDao.insertApplyRoleRelation(applyUserRelation);

	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryAllApplyStoreSate(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryAllApplyStoreSate(Map<String, Object> param) {
		return applyStoreDao.queryAllApplyStoreSate(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryFinanceApproveStoreTry(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryFinanceApproveStoreTry(Map<String, Object> param) {
		return applyStoreDao.queryFinanceApproveStoreTry(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryManagersApproveStoreTry(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryManagersApproveStoreTry(Map<String, Object> param) {
		return applyStoreDao.queryManagersApproveStoreTry(param);
	}

	/**
	 * @throws Exception
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#roleApproveStore(java.util.Map)
	 */

	@Override
	public Integer roleApproveStore(Map<String, Object> param) throws Exception {
		Integer roleId = (Integer) param.get("roleId");
		Integer userId = (Integer) param.get("userId");
		Integer applyStoreId = (Integer) param.get("applyStoreId");
		Integer approveState = (Integer) param.get("approveState");

		ApplyUserRelation applyUserRelation = new ApplyUserRelation();// 操作记录
		applyUserRelation.setApplyId(applyStoreId);
		applyUserRelation.setUserid(userId);// userId
		applyUserRelation.setRoleid(roleId);

		if (ConstantsRole.ROLE_FINANCE.equals(roleId)) {// 财务
			if (approveState == ConstantStorePower.approve_state_succ) {// 财务同意
				financeApproveApply(applyStoreId, approveState, applyUserRelation);
			}

		} else if (ConstantsRole.ROLE_MANAGERS.equals(roleId)) {// 主管
			applyUserRelation.setCaurApproveState(approveState);
			if (approveState == ConstantStorePower.approve_state_succ) {// 主管同意
				managersApproveApply(applyStoreId, approveState, applyUserRelation);
			} else if (approveState == ConstantStorePower.approve_state_fail) {// 主管拒绝
				managersNotApproveApply(applyStoreId, approveState, applyUserRelation);

			}
		}
		return null;
	}

	/**
	 * 主管拒绝
	 * @param applyStoreId
	 * @param approveState
	 * @param applyUserRelation
	 */

	void managersNotApproveApply(Integer applyStoreId, Integer approveState, ApplyUserRelation applyUserRelation) {
		applyUserRelation.setApplyId(applyStoreId);
		applyUserRelation.setCaurApproveState(approveState);
		this.insertApplyRoleRelation(applyUserRelation);

		ApplyStore applyStore = new ApplyStore();
		applyStore.setApplyStoreId(applyStoreId);
		applyStore.setApplyStatus(ConstantStorePower.approve_state_fail);// 单子的状态2
		applyStore.setManagersCheck(ConstantStorePower.approve_state_fail);// 单子的经理审核状态2
		this.updateApplyStore(applyStore);
	}

	/**
	 * 主管同意
	 * @param applyStoreId
	 * @param approveState
	 * @param applyUserRelation
	 */

	void managersApproveApply(Integer applyStoreId, Integer approveState, ApplyUserRelation applyUserRelation) {
		applyUserRelation.setApplyId(applyStoreId);
		applyUserRelation.setCaurApproveState(approveState);
		this.insertApplyRoleRelation(applyUserRelation);

		ApplyStore applyStore = new ApplyStore();
		applyStore.setApplyStoreId(applyStoreId);
		applyStore.setWhoCheck(0);
		applyStore.setRoleApprove(ConstantsRole.ROLE_FINANCE);// 财务
		applyStore.setApplyStatus(ConstantStorePower.apply_state_ready);// 单子的状态0
		applyStore.setManagersCheck(ConstantStorePower.approve_state_succ);// 单子的经理审核状态1
		applyStore.setWhetherStartApply(ConstantStorePower.WHETHER_STARTAPPLY_NO);// 重置0
		this.updateApplyStore(applyStore);
	}

	/**
	 * 财务同意
	 * @param applyStoreId
	 * @param approveState
	 * @param applyUserRelation
	 * @throws Exception
	 */

	void financeApproveApply(Integer applyStoreId, Integer approveState, ApplyUserRelation applyUserRelation)
			throws Exception {
		// 插入到记录表
		applyUserRelation.setApplyId(applyStoreId);
		applyUserRelation.setCaurApproveState(approveState);
		this.insertApplyRoleRelation(applyUserRelation);

		Map<String, Object> queryApplyStoreDetails = this.queryApplyStoreDetails(applyStoreId);
		Integer paymoneyType = (Integer) queryApplyStoreDetails.get("paymoneyType");
		ApplyStore applyStore = new ApplyStore();
		applyStore.setApplyStoreId(applyStoreId);// id
		if (ConstantStorePower.APPLY_PAYMONEY_NOTALL.equals(paymoneyType)) {// 定金
			applyStore.setApplyStatus(ConstantStorePower.apply_state_ready);// 单子的定金通过--in
			applyStore.setFinanceCheck(ConstantStorePower.apply_state_succ);// 单子的财务状态
			applyStore.setWhetherStartApply(ConstantStorePower.WHETHER_STARTAPPLY_NO);// 没有
		} else {// 全款
			applyStore.setApplyStatus(ConstantStorePower.apply_state_succ);// 单子的状态
			applyStore.setFinanceCheck(ConstantStorePower.apply_state_succ);// 单子的财务状态
			updateDealershipNum(applyStoreId);// 更新城市的经销权个数

		}

		this.updateApplyStore(applyStore);
		if (ConstantStorePower.APPLY_PAYMONEY_ALL.equals(paymoneyType)) {// 全额
			ApplyStore applyStoreCopy = this.applyStoreDao.selectByAppStoreId(applyStoreId);
			Integer storeNumm = applyStoreCopy.getStoreNumm();
			if (storeNumm != null) {
				for (int i = 0; i < storeNumm; i++) {
					ApplyStoreDetail applyDetailStore = new ApplyStoreDetail();
					try {
						PropertyUtils.copyProperties(applyDetailStore, applyStoreCopy);
						applyDetailStore.setApplyStatus(1);
						applyStoreDetailDao.addApplyStore(applyDetailStore);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param applyStoreId
	 * @return
	 */

	public ApplyStore selectByAppStoreId(Integer applyStoreId) {
		return applyStoreDao.selectByAppStoreId(applyStoreId);
	}

	/**
	 * 更新城市的经销权个数
	 * @param applyStoreId
	 * @throws Exception
	 */

	void updateDealershipNum(Integer applyStoreId) throws Exception {
		Map<String, Object> queryApplyStoreDetails = applyStoreDao.queryApplyStoreDetails(applyStoreId);
		Integer applyType = (Integer) queryApplyStoreDetails.get("applyType");
		if (ConstantStorePower.APPLY_TYPE_DEALERSHIP.equals(applyType)) {// 是经销权才更新
			Integer cityId = (Integer) queryApplyStoreDetails.get("cityId");// 用户的城市id
			Integer districtId = (Integer) queryApplyStoreDetails.get("districtId");// 用户的城市id
			Integer dealershipNum = (Integer) queryApplyStoreDetails.get("dealershipNum");// 用户的经销权个数
			if (dealershipNum != null && dealershipNum != 0) {
				areaService.updateCitySellInfo(cityId, districtId, dealershipNum);
			}
		}

	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryApproveMsg(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryApproveMsg(Map<String, Object> param) {
		return applyUserRelationDao.queryApproveMsg(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#printContract(Map)
	 */

	@Override
	public Map<String, Object> printContract(Map<String, Object> param) {
		return applyStoreDao.printContract(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryInviteUserDet(java.util.Map)
	 */

	@Override
	public Map<String, Object> queryInviteUserDet(Map<String, Object> param) {
		return applyFlowDao.queryInviteUserDet(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryInviteUserMsgDet(java.util.Map)
	 */

	@Override
	public Map<String, Object> queryInviteUserMsgDet(Map<String, Object> param) {
		return applyFlowDao.queryInviteUserMsgDet(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#updateInviteUserById(java.util.Map)
	 */

	@Override
	public Integer updateInviteUserById(Map<String, Object> param) {
		return applyFlowDao.updateInviteUserById(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryRoleApproveStoreTry(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryRoleApproveStoreTry(Map<String, Object> param) {
		return applyStoreDao.queryRoleApproveStoreTry(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryBankList(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryBankList(Map<String, Object> param) {
		return bankMapper.queryBankList(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#updateWhetherStartApply(com.zaijiadd.app.applyflow.entity.ApplyStore)
	 */

	@Override
	public Integer updateWhetherStartApply(ApplyStore applyStore) {
		return applyStoreDao.updateWhetherStartApply(applyStore);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryDealershipNumAble(java.lang.Integer)
	 */

	@Override
	public Map<String, Object> queryDealershipNumAble(Integer cityId) {
		return cityDealershipMapper.queryDealershipNumAble(cityId);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#updateUserAddFlagById(java.util.Map)
	 */

	@Override
	public Integer updateUserAddFlagById(Map<String, Object> param) {
		return applyFlowDao.updateUserAddFlagById(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryAllInviteUserMsg(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryAllInviteUserMsg(Map<String, Object> param) {
		return applyFlowDao.queryAllInviteUserMsg(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryInviteUserMsgLike(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryInviteUserMsgLike(Map<String, Object> param) {
		return applyFlowDao.queryInviteUserMsgLike(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#generateSerialNum()
	 */

	@Override
	public String generateSerialNum() {
		SerialNumber serialNumberEntity = new SerialNumber();
		Integer SerialNumber1 = serialNumberMapper.generateSerialNum(serialNumberEntity);
		Long serialNumber = serialNumberEntity.getSerialNumber();
		String serialNumberStr = String.valueOf(serialNumber);
		StringBuilder stringBuilder = new StringBuilder();

		if (serialNumberStr.length() <= 6) {
			int zeroNum = 6 - serialNumberStr.length();
			for (int i = 0; i < zeroNum; i++) {
				stringBuilder.append(0);

			}
			stringBuilder.append(serialNumberStr);
		} else {
			stringBuilder.append(serialNumberStr);
		}
		return stringBuilder.toString();
	}

	/**
	 * @throws Exception
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#payRemainMoney(com.zaijiadd.app.applyflow.entity.ApplyStore)
	 */

	@Override
	public String payRemainMoney(ApplyStore applyStore) throws Exception {
		BigDecimal contractAmount = applyStore.getContractAmount();
		BigDecimal needPaymoney = applyStore.getNeedPaymoney();
		BigDecimal paidMoney = applyStore.getPaidMoney();
		Integer payWay = applyStore.getPayWay();
		Integer paymoneyType2 = applyStore.getPaymoneyType();
		Integer whetherSelf = applyStore.getWhetherSelf();
		String payAlipayNum = applyStore.getPayAlipayNum();
		String bankNumone = applyStore.getBankNumone();
		String payBankName = applyStore.getPayBankName();
		String paySubbranchBank = applyStore.getPaySubbranchBank();

		ApplyStore applyStore2 = this.selectByAppStoreId(applyStore.getApplyStoreId());// 老的记录

		ApplyStore applyStore3 = new ApplyStore();

		BeanUtils.copyProperties(applyStore3, applyStore2);
		applyStore3.setFinanceCheck(ConstantStorePower.approve_state_ready);
		applyStore3.setManagersCheck(ConstantStorePower.approve_state_ready);
		applyStore3.setApplyStatus(ConstantStorePower.apply_state_ready);
		applyStore3.setWhetherStartApply(ConstantStorePower.WHETHER_STARTAPPLY_NO);
		applyStore3.setContractAmount(contractAmount);
		applyStore3.setNeedPaymoney(needPaymoney);
		applyStore3.setPaidMoney(paidMoney);
		applyStore3.setPayWay(payWay);
		applyStore3.setPaymoneyType(paymoneyType2);
		applyStore3.setWhetherSelf(whetherSelf);
		applyStore3.setWhoCheck(null);
		applyStore3.setPossNum(null);
		applyStore3.setPayAlipayNum(payAlipayNum);
		applyStore3.setBankNumone(bankNumone);
		applyStore3.setPayBankName(payBankName);
		applyStore3.setPaySubbranchBank(paySubbranchBank);
		//
		Integer applyStoreId = applyStoreDao.addApplyStore(applyStore3);//
		Integer applyStoreId2 = applyStore3.getApplyStoreId();
		applyStore3.setApplyStoreId(applyStoreId2);
		// 新记录
		Integer applyType = applyStore3.getApplyType();
		Integer paymoneyType = applyStore3.getPaymoneyType();// 付款类型
		whoCheckpayRemainMoney(applyStore3, applyType, paymoneyType);
		updateApplyStore(applyStore3);
		// 把原来的记录备份
		applyStore2.setApplyStatus(ConstantStorePower.APPLY_STATE_NOT_PAYALLMONEY);// 尾款，用户不可见
		updateApplyStore(applyStore2);
		String possNum = null;
		if (ConstantStorePower.APPLY_PAY_WAY_SWIPING_CARD.equals(applyStore3.getPayWay())) {// 刷卡
			possNum = generateSerialNum();// 生成流水号
			ApplyStore applyStore4 = new ApplyStore();
			applyStore4.setPossNum(possNum);
			applyStore4.setApplyStoreId(applyStore3.getApplyStoreId());
			updateApplyStore(applyStore4);

		}

		return possNum;
	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param applyStore
	 * @param applyType
	 * @param paymoneyType
	 * @throws Exception
	 */

	private void whoCheckpayRemainMoney(ApplyStore applyStore, Integer applyType, Integer paymoneyType)
			throws Exception {
		Map<String, Object> applyStoreMap = queryApplyStoreDetails(applyStore.getApplyStoreId());
		applyType = (Integer) applyStoreMap.get("applyType");
		Integer cityId = (Integer) applyStoreMap.get("cityId");
		Integer dealershipNum = (Integer) applyStoreMap.get("dealershipNum");
		Integer storeNumm = (Integer) applyStoreMap.get("storeNumm");
		Integer userId = (Integer) applyStoreMap.get("userId");

		applyStore.setCityId(cityId);
		applyStore.setDealershipNum(dealershipNum);
		applyStore.setStoreNumm(storeNumm);
		applyStore.setYjsUserId(userId);
		whoCheck(applyStore, applyType, paymoneyType);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#whetherPayAllMoney(com.zaijiadd.app.applyflow.entity.ApplyStore)
	 */

	@Override
	public Boolean whetherPayAllMoney(ApplyStore applyStore) throws Exception {
		Integer applyType = applyStore.getApplyType();// 申请类型
		if (applyType.equals(ConstantStorePower.APPLY_TYPE_DEALERSHIP)) {// 经销权
			BigDecimal personPaymoneyCount = getPersonPaymoneyCount(applyStore);
			// 经销权价格计算
			BigDecimal needPaymoneyCount = getDealershipMoney(applyStore);

			if (personPaymoneyCount.compareTo(needPaymoneyCount) < 0) {// 没有支付全额
				return false;
			}
		} else if (applyType.equals(ConstantStorePower.APPLY_TYPE_SMALLSTORE)) {// 小店
			BigDecimal personPaymoneyCount = getPersonPaymoneyCount(applyStore);
			// 小店价格计算
			BigDecimal needPaymoneyCount = getNeedPaymoneyCount(applyStore);

			if (personPaymoneyCount.compareTo(needPaymoneyCount) < 0) {// 没有支付全额
				return false;
			}
		}

		return true;
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#cleanLoseEfficacyApplyStore()
	 */

	@Override
	public void cleanLoseEfficacyApplyStore() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applyStatus", ConstantStorePower.apply_state_ready);
		param.put("whetherStartApply", ConstantStorePower.WHETHER_STARTAPPLY_NO);
		List<ApplyStore> applyStoreList = applyStoreDao.queryApplStoreNotAllMoney(param);// 订单状态为0
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long thirdTime = (long) (1 * 72 * 60 * 60 * 1000);
		for (ApplyStore applyStore : applyStoreList) {
			Date createdDate = null;
			if (getHandselSucc(applyStore)) {// 定金且财务审核成功
				HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
				paramHashMap.put("roleId", ConstantsRole.ROLE_FINANCE);// 财务
				paramHashMap.put("caurApproveState", ConstantStorePower.approve_state_succ);
				paramHashMap.put("applyId", applyStore.getApplyStoreId());
				List<ApplyUserRelation> applyUserRelation = applyUserRelationDao
						.getRoleOperationRecordByApplyId(paramHashMap);
				if (applyUserRelation != null && applyUserRelation.size() > 0) {
					createdDate = applyUserRelation.get(0).getCaurCreatedDate();
				}

			} else {
				createdDate = applyStore.getCreatedDate();
			}
			exceedThreeDayApplyFail(thirdTime, applyStore, createdDate);

		}

	}

	/**
	 * 超过三天把单子的状态更新为失败
	 * @param thirdTime
	 * @param applyStore
	 * @param createdDate
	 */

	void exceedThreeDayApplyFail(Long thirdTime, ApplyStore applyStore, Date createdDate) {
		long time = createdDate.getTime();
		long todayTime = new Date().getTime();
		if (todayTime - time > thirdTime) {// 超过了三天
			ApplyStore applyStore2 = new ApplyStore();
			applyStore2.setApplyStoreId(applyStore.getApplyStoreId());
			applyStore2.setApplyStatus(ConstantStorePower.apply_state_fail);
			updateApplyStore(applyStore2);// 更新状态
		}
	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @param applyStore
	 * @return
	 */

	boolean getHandselSucc(ApplyStore applyStore) {
		Integer applyStatus = applyStore.getApplyStatus();
		Integer paymoneyType = applyStore.getPaymoneyType();// 定金
		if (applyStore.getFinanceCheck() != null
				&& ConstantStorePower.approve_state_succ.equals(applyStore.getFinanceCheck())
				&& ConstantStorePower.APPLY_PAYMONEY_NOTALL.equals(paymoneyType)) {// 定金-财务审核通过
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#getApplyContract(java.util.Map)
	 */

	@Override
	public ApplyContract getApplyContract(Map<String, Object> applyContractParam) {
		return applyContractMapper.getApplyContract(applyContractParam);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#getUserInfoById(java.lang.Integer)
	 */

	@Override
	public UserInfoEntity getUserInfoById(Integer userId) {
		return userInfoDao.getUserInfoById(userId);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryAllApplyStoreSateIn(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryAllApplyStoreSateIn(Map<String, Object> param) {
		return applyStoreDao.queryAllApplyStoreSateIn(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryByParamCount(java.util.Map)
	 */

	@Override
	public Integer queryByParamCount(Map<String, Object> param) {
		return applyStoreDao.queryByParamCount(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryApproveMsgCount(java.util.Map)
	 */

	@Override
	public Integer queryApproveMsgCount(Map<String, Object> param) {
		return applyUserRelationDao.queryApproveMsgCount(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryApplyDealershipNum(java.util.Map)
	 */

	@Override
	public List<Map<String, Object>> queryApplyDealershipNum(Map<String, Object> param) {

		Integer cityId = (Integer) param.get("cityId");
		Integer countryId = (Integer) param.get("districtId");
		// Integer applyStatus = (Integer) param.get("applyStatus");

		List<Country> list = this.countryMapper.selectByCounryId(countryId);
		Country countryObj = list.get(0);
		String countryName = countryObj.getCountryName();
		int cityIndexOf = countryName.lastIndexOf("市");
		int countryIndexOf = countryName.lastIndexOf("县");
		int length = countryName.length();

		Map<String, Object> param2 = new HashMap<String, Object>();
		if (cityId != 2 && ((cityIndexOf == -1 || length != (cityIndexOf + 1)))
				&& ((countryIndexOf == -1 || length != (countryIndexOf + 1)))) {// 市
			param2.put("cityId", cityId);
		} else {
			param2.put("cityId", cityId);
			param2.put("districtId", countryId);

		}
		return applyStoreDao.queryApplyDealershipNum(param2);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#addApplyStoreNew(com.zaijiadd.app.applyflow.entity.ApplyStore)
	 */

	@Override
	public Integer addApplyStoreNew(ApplyStore applyStore3) {
		return applyStoreDao.addApplyStore(applyStore3);
	}

	/**
	 * @return
	 * @throws Exception
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#handleUpdate(com.zaijiadd.app.applyflow.entity.ApplyStore)
	 */

	@Override
	public String handleUpdate(ApplyStore applyStore) throws Exception {
		ApplyStore applyStore2 = this.selectByAppStoreId(applyStore.getApplyStoreId());// 老的记录
		ApplyStore applyStore3 = new ApplyStore();// 新的记录

		BeanUtils.copyProperties(applyStore3, applyStore);//
		applyStore3.setApplyStatus(ConstantStorePower.apply_state_ready);// 待申请状态
		applyStore3.setWhetherStartApply(ConstantStorePower.WHETHER_STARTAPPLY_NO);// 没有发起收款申请
		applyStore3.setFinanceCheck(ConstantStorePower.approve_state_ready);
		applyStore3.setManagersCheck(ConstantStorePower.approve_state_ready);
		Integer applyStoreId1 = this.addApplyStoreNew(applyStore3);// 新记录
		applyStore3.setApplyStoreId(applyStoreId1);

		// 新记录去走流程
		Integer applyType = applyStore3.getApplyType();
		Integer paymoneyType = applyStore3.getPaymoneyType();// 付款类型
		whoCheck(applyStore3, applyType, paymoneyType);
		updateApplyStore(applyStore3);

		// 把原来的记录备份
		applyStore2.setApplyStatus(ConstantStorePower.APPLY_STATE_NOT_PAYALLMONEY);// 尾款，用户不可见
		updateApplyStore(applyStore2);
		String possNum = generateSerialNum();// 生成流水号
		return possNum;
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryByParamInviteUserCount(java.util.Map)
	 */

	@Override
	public Integer queryByParamInviteUserCount(Map<String, Object> param) {
		return applyFlowDao.queryByParamInviteUserCount(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#queryByParamInviteUserCountLike(java.util.Map)
	 */

	@Override
	public Integer queryByParamInviteUserCountLike(Map<String, Object> param) {
		return applyFlowDao.queryByParamInviteUserCountLike(param);
	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#generateContractNum(java.lang.Integer,
	 *      Date)
	 */

	@Override
	public String generateContractNum(Integer applyStoreId, String contractDate) {
		// 有数据,看日期是否是同一天，是同一天，加1，不是更新日期，并重置为0
		Integer dataCount = contractNumMapper.selectDataCount();// 数据库数量
		if (dataCount != null && dataCount > 0) {
			ContractNum contractNum = contractNumMapper.queryContractNum();// 查询所有
			Integer contractNumSum = contractNum.getContractNumSum();// 总数
			Integer contractNumId = contractNum.getContractNumId();
			Date createdDate = contractNum.getCreatedDate();
			Date contractNumTime = contractNum.getContractNumTime();// 合同日期
			String contractNumTimeStr = DateUtils.getSysDate(contractNumTime, "yyyy-MM-dd");
			Map<String, Object> param = new HashMap<String, Object>();

			if (contractDate.equals(contractNumTimeStr)) {// 同一天+1更新
				contractNumSum++;
			} else {// 不是同一天，置为0
				contractNumSum = 0;
				param.put("contractNumTime", contractDate);// 时间

			}
			param.put("contractNumSum", contractNumSum);// 总数
			param.put("contractNumId", contractNumId);
			contractNumMapper.updateContractNum(param);
			return String.valueOf(contractNumSum);
		} else {// 先查询数据是否有数据，没有插入
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("contractNumSum", 0);
			param.put("contractNumTime", contractDate);
			contractNumMapper.insertContractNum(param);
			return String.valueOf(0);

		}

	}

	/**
	 * @see com.zaijiadd.app.applyflow.service.ApplyFlowService#getFinanceApproveSucce(java.lang.Integer,
	 *      java.lang.Integer)
	 */

	@Override
	public String getFinanceApproveSucce(Integer applyStoreId, Integer userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applyStoreId", applyStoreId);
		param.put("applyStatus", ConstantStorePower.apply_state_succ);// 申请单是成功的
		List<ApplyUserRelation> applyUserRelation = applyUserRelationDao.getFinanceApproveSucce(param);
		if (applyUserRelation != null && applyUserRelation.size() > 0) {
			ApplyUserRelation applyUserRelation2 = applyUserRelation.get(0);
			Date caurCreatedDate = applyUserRelation2.getCaurCreatedDate();
			String caurCreatedDateStr = DateUtils.getSysDate(caurCreatedDate, "yyyy-MM-dd");
			return caurCreatedDateStr;
		} else {
			String sysDate = DateUtils.getSysDate(new Date(), "yyyy-MM-dd");
			return sysDate;
		}

	}
}
