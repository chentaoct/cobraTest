package com.zaijiadd.app.external.dao;

import java.util.List;
import java.util.Map;

public interface ExternalDataDAO {
	
	public List<Map<String, Object>> importHomeVisitorData( Map<String,Object> param );

}
