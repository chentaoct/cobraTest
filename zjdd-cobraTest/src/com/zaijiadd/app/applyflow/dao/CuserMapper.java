/**
 * @(#)CuserMapper.java 2015年12月21日 Copyright 2015 it.kedacom.com, Inc. All
 *                      rights reserved.
 */

package com.zaijiadd.app.applyflow.dao;

import java.util.List;

import com.zaijiadd.app.applyflow.entity.Cuser;

/**
 * (用一句话描述类的主要功能)
 * @author chentao
 * @date 2015年12月21日
 */

public interface CuserMapper {

	/**
	 * (用一句话描述方法的主要功能)
	 * @param i
	 * @return
	 */

	List<Cuser> selectUserOrderByUId(int i);

	/**
	 * (用一句话描述方法的主要功能)
	 * @param i
	 * @return
	 */

	List<Cuser> selectUserOrderByUIdMap(int i);

}
