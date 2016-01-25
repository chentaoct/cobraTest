package com.zaijiadd.app.push.ios;

import com.zaijiadd.app.push.IOSNotification;

public class IOSUnicast extends IOSNotification {
	
	public IOSUnicast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IOSUnicast(String appkey,String appMasterSecret) throws Exception{
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }
}
