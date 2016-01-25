package com.zaijiadd.app.push.ios;

import com.zaijiadd.app.push.IOSNotification;

public class IOSBroadcast extends IOSNotification {
	
	public IOSBroadcast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IOSBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
		
	}
}
