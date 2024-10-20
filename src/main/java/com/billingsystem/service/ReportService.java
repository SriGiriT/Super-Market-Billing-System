package com.billingsystem.service;

import java.sql.ResultSet;

import com.billingsystem.DAO.ReportDao;

public class ReportService {
	ReportDao reportDao = new ReportDao();
	public ResultSet getTopSellingProducts() {
		return reportDao.getTopSessingProducts();
	}
	public ResultSet getTopCustomers() {
		return reportDao.getTopCustomers();
	}
	public ResultSet getInactiveCustomers() {
		return reportDao.getInactiveCustomers();
	}
	public ResultSet getDailyRevenue() {
		return reportDao.getDailyRevenue();
	}
	public ResultSet getSignedUpButNoPurchase() {
		return reportDao.getSignedUpButNoPurchase();
	}
	public ResultSet getLowStockProducts() {
		return reportDao.getLowStockProducts();
	}
	public ResultSet getfrequentlyBoughtItem(int userId) {
		return reportDao.getfrequentlyBoughtItem(userId);
	}

}
