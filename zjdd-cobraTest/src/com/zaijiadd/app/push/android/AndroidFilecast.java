package com.zaijiadd.app.push.android;

import com.zaijiadd.app.push.AndroidNotification;

public class AndroidFilecast extends AndroidNotification {
	
	public AndroidFilecast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AndroidFilecast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}