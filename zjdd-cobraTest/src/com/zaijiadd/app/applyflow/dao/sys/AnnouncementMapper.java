package com.zaijiadd.app.applyflow.dao.sys;

import java.util.List;
import java.util.Map;

import com.zaijiadd.app.applyflow.entity.sys.Announcement;
import com.zaijiadd.app.applyflow.entity.sys.VOAnnouncement;

public interface AnnouncementMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(Announcement record);

    int insertSelective(Announcement record);

    Announcement selectByPrimaryKey(Long id);
    
    List<Announcement> select(Map<String, Object> map);
    
    List<VOAnnouncement> selectByUserId(Map<String, Object> map);
    
    int selectCount(Map<String, Object> map);

    int updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);
    
    int updateUserStatus(Map<String, Object> map);
}