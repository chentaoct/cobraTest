package com.zaijiadd.app.applyflow.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.zaijiadd.app.applyflow.dao.sys.AnnouncementMapper;
import com.zaijiadd.app.applyflow.entity.sys.Announcement;
import com.zaijiadd.app.applyflow.entity.sys.VOAnnouncement;
import com.zaijiadd.app.applyflow.service.AnnouncementService;

public class AnnouncementServiceImpl implements AnnouncementService {
	
	@Autowired
	private AnnouncementMapper announcementMapper;

	@Override
	public int deleteByPrimaryKey(Long id) throws Exception {
		return 0;
	}

	@Override
	public int insertSelective(Announcement record) throws Exception {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		record.setCreatedTime(timestamp);
		record.setReleaseTime(timestamp);
		record.setUpdatedTime(timestamp);
		return this.announcementMapper.insertSelective(record);
	}

	@Override
	public Announcement selectByPrimaryKey(Long id) throws Exception {
		return this.announcementMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Announcement record) throws Exception {
		return this.announcementMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Announcement> select(Map<String, Object> map) throws Exception {
		return this.announcementMapper.select(map);
	}

	@Override
	public List<VOAnnouncement> selectByUserId(Map<String, Object> map) throws Exception {
		return this.announcementMapper.selectByUserId(map);
	}

	@Override
	public int selectCount(Map<String, Object> map) throws Exception {
		return this.announcementMapper.selectCount(map);
	}

	@Override
	public int updateUserStatus(Map<String, Object> map) throws Exception {
		return this.announcementMapper.updateUserStatus(map);
	}

}
