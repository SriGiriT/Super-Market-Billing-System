package com.billingsystem.service;

import com.billingsystem.DAO.TransactionsDao;
import com.billingsystem.Model.Transactions;

public class TransactionsService {
	private TransactionsDao transactionsDao = new TransactionsDao();
	public void saveTransactions(Transactions transaction) {
		transactionsDao.saveTransactions(transaction);
	}
	public double getTotalTransaction() {
		return transactionsDao.getTotalTransaction();
	}
}
