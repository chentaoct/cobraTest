package com.zaijiadd.app.push.android;

import org.json.JSONObject;

import com.zaijiadd.app.push.AndroidNotification;

public class AndroidGroupcast extends AndroidNotification {
	
	public AndroidGroupcast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AndroidGroupcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
