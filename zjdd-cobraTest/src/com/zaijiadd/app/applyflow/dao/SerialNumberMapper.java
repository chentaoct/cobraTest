/**
 * @(#)SerialNumberMapper.java 2015年12月15日 Copyright 2015 it.kedacom.com, Inc.
 *                             All rights reserved.
 */

package com.zaijiadd.app.applyflow.dao;

import com.zaijiadd.app.applyflow.entity.SerialNumber;

/**
 * (用一句话描述类的主要功能)
 * @author chentao
 * @date 2015年12月15日
 */

public interface SerialNumberMapper {

	/**
	 * (用一句话描述方法的主要功能)
	 * @param serialNumber
	 * @return
	 */

	Integer generateSerialNum(SerialNumber serialNumber);

}
