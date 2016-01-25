package com.zaijiadd.app.push.ios;

import com.zaijiadd.app.push.IOSNotification;

public class IOSFilecast extends IOSNotification {
	
	public IOSFilecast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IOSFilecast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}
