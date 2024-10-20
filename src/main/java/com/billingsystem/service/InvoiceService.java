package com.billingsystem.service;

import com.billingsystem.DAO.InvoiceDao;
import com.billingsystem.Model.Invoice;

public class InvoiceService {
	private InvoiceDao invoiceDao = new InvoiceDao();
	  public  void saveInvoice(Invoice invoice) {
	        invoiceDao.saveInvoice(invoice);
	    }
}	
