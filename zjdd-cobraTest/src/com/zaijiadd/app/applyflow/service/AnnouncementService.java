package com.zaijiadd.app.applyflow.service;

import java.util.List;
import java.util.Map;

import com.zaijiadd.app.applyflow.entity.sys.Announcement;
import com.zaijiadd.app.applyflow.entity.sys.VOAnnouncement;

/**
 * 公告管理
 * @author Gary Guo
 *
 */
public interface AnnouncementService {
	
	int deleteByPrimaryKey(Long id) throws Exception;


    int insertSelective(Announcement record) throws Exception;

    Announcement selectByPrimaryKey(Long id) throws Exception;
    
    List<Announcement> select(Map<String, Object> map) throws Exception;
    
    List<VOAnnouncement> selectByUserId(Map<String, Object> map) throws Exception;
    
    int selectCount(Map<String, Object> map) throws Exception;

    int updateByPrimaryKeySelective(Announcement record) throws Exception;
    
    /**
     * 用户查看公告记录
     * @param map
     * @return
     * @throws Exception
     */
    int updateUserStatus(Map<String, Object> map) throws Exception;
    

}
