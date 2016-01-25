package com.zaijiadd.app.applyflow.entity.sys;

import java.io.Serializable;

public class HelpCenter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7815007723700280874L;
	private String bank;
	private String accountName;
	private String bankAccount;
	private String tutorial;
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getTutorial() {
		return tutorial;
	}
	public void setTutorial(String tutorial) {
		this.tutorial = tutorial;
	}
	
}
