/**
 * @(#)ContractNumMapper.java 2015年12月15日 Copyright 2015 it.kedacom.com, Inc.
 *                            All rights reserved.
 */

package com.zaijiadd.app.applyflow.dao;

import java.util.Map;

import com.zaijiadd.app.applyflow.entity.ContractNum;

/**
 * (用一句话描述类的主要功能)
 * @author chentao
 * @date 2015年12月15日
 */

public interface ContractNumMapper {

	/**
	 * (用一句话描述方法的主要功能)
	 * @return
	 */

	Integer selectDataCount();

	/**
	 * (用一句话描述方法的主要功能)
	 * @return
	 */

	ContractNum queryContractNum();

	/**
	 * (用一句话描述方法的主要功能)
	 * @param param
	 */

	void updateContractNum(Map<String, Object> param);

	/**
	 * (用一句话描述方法的主要功能)
	 * @param param
	 */

	void insertContractNum(Map<String, Object> param);

}
