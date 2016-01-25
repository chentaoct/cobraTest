package com.zaijiadd.app.push.android;

import com.zaijiadd.app.push.AndroidNotification;

public class AndroidUnicast extends AndroidNotification {
	
	public AndroidUnicast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AndroidUnicast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");	
	}
		
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }

}