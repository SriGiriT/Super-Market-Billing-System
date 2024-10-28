package com.billingsystem.service;


import com.billingsystem.DAO.ReportDao;

public class ReportService {
	ReportDao reportDao = new ReportDao();
	public String getTopSellingProducts() {
		return reportDao.getTopSessingProducts();
	}
	public String getTopCustomers() {
		return reportDao.getTopCustomers();
	}
	public String getInactiveCustomers() {
		return reportDao.getInactiveCustomers();
	}
	public String getDailyRevenue() {
		return reportDao.getDailyRevenue();
	}
	public String getSignedUpButNoPurchase() {
		return reportDao.getSignedUpButNoPurchase();
	}
	public String getLowStockProducts() {
		return reportDao.getLowStockProducts();
	}
	public String getfrequentlyBoughtItem(int userId) {
		return reportDao.getfrequentlyBoughtItem(userId);
	}
	public String getAllBillStatemetns(int userId) {
		return reportDao.getAllBillStatements(userId);
	}
	public String getTopCashier() {
		return reportDao.getTopCashier();
	}
	public String getUnsoldProducts() {
		return reportDao.getUnsoldProducts();
	}
	public String getOutOfStockProducts() {
		return reportDao.getOutOfStockProducts();
	}
	public String getPurchaseDetails(int transactionId) {
		return reportDao.getPurchaseDetails(transactionId);
	}

}
