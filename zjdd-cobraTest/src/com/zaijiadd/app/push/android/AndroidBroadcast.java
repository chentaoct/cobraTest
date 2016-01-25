package com.zaijiadd.app.push.android;

import com.zaijiadd.app.push.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification {
	
	public AndroidBroadcast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
	}
}
