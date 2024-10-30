package com.billingsystem.service;

import com.billingsystem.DAO.VerificationDao;

public class VerificationService {
	private VerificationDao verificationDao = new VerificationDao();
	public void saveVerificationCode(long user_id, String verificationCode) {
		verificationDao.saveVerificationCode(user_id, verificationCode);
	}
	public boolean isValidEmailUser(long user_id, String verificationCode) {
		return verificationDao.isValidEmailUser(user_id, verificationCode);
	}
}
