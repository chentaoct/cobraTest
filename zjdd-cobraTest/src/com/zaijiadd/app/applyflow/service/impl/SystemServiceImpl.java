package com.zaijiadd.app.applyflow.service.impl;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.zaijiadd.app.applyflow.entity.sys.HelpCenter;
import com.zaijiadd.app.applyflow.service.SystemService;
import com.zaijiadd.app.utils.constants.ConstantsForAccount;

@Service
public class SystemServiceImpl implements SystemService {

	@Override
	public HelpCenter helpCenter() throws Exception {
		
		//System.out.println(SystemServiceImpl.class.getResource("/") + "config" + File.separator + "help.xml");
		SAXReader reader = new SAXReader();
		String filePath = this.getClass().getResource("/") + "config" + File.separator + "help.xml";
		File xmlFile = new File(filePath.substring(5));
		if(xmlFile.exists()) {
			System.out.println();
		}
		Document   document = reader.read(xmlFile); 
		Element root = document.getRootElement(); 
		Element element = root.element("Bank");
		HelpCenter helpCenter = new HelpCenter();
		helpCenter.setBank(root.elementText("Bank"));
		helpCenter.setAccountName(root.elementText("AccountName"));
		helpCenter.setBankAccount(root.elementText("BankAccount"));
		helpCenter.setTutorial(ConstantsForAccount.IMG_URL + root.elementText("Tutorial"));
		return helpCenter;
	}

}
