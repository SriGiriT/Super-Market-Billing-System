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
	public ResultSet getAllBillStatemetns(int userId) {
		return reportDao.getAllBillStatements(userId);
	}
	public ResultSet getTopCashier() {
		return reportDao.getTopCashier();
	}
	public ResultSet getUnsoldProducts() {
		return reportDao.getUnsoldProducts();
	}
	public ResultSet getOutOfStockProducts() {
		return reportDao.getOutOfStockProducts();
	}

}
