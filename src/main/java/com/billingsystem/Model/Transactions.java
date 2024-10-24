package com.billingsystem.Model;

import java.time.LocalDateTime;

public class Transactions {
	private int transaction_id;
	private long customer_id;
	private long cashier_id;
	private double amount;
	private LocalDateTime transaction_date;
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDateTime transaction_date) {
		this.transaction_date = transaction_date;
	}
	
	public long getCashier_id() {
		return cashier_id;
	}
	public void setCashier_id(long cashier_id) {
		this.cashier_id = cashier_id;
	}
	public Transactions(int transaction_id, int customer_id, double amount, LocalDateTime transaction_date) {
		super();
		this.transaction_id = transaction_id;
		this.customer_id = customer_id;
		this.amount = amount;
		this.transaction_date = transaction_date;
	}
	
	public Transactions(){
		
	}
	@Override
	public String toString() {
		return "Transactions [transaction_id=" + transaction_id + ", customer_id=" + customer_id + ", amount=" + amount
				+ ", transaction_date=" + transaction_date + "]";
	}
	
	
}
