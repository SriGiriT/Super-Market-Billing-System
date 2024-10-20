package com.billingsystem.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.billingsystem.service.CouponService;


@WebServlet("/CouponCode")
public class CouponCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CouponService couponService = new CouponService();
		double overallAmount = Double.parseDouble(request.getParameter("overallAmount"));
		String coupon = request.getParameter("couponCode");
		Map<String, List<String>> hashMap = couponService.getCouponCodes();
		List<String> criteria = hashMap.get(coupon);
		System.out.println(criteria);
		if(criteria != null) {
			double minAmount = Double.parseDouble(criteria.get(0));
			String couponCriteria = criteria.get(1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
			LocalDate expiryDate = LocalDate.parse(criteria.get(2), formatter);
			LocalDate todayDate = LocalDate.now();
			String offerAmount = criteria.get(3);
			Double providedOffer = 0.0;
			boolean isPersentageOffer = offerAmount.endsWith("%");
			if(todayDate.isAfter(expiryDate)) {
				request.setAttribute("providedOffer", 0);
				request.setAttribute("couponMessage", "Coupon Got Expired!");
			}else if(overallAmount < minAmount) {
				request.setAttribute("providedOffer", 0);
				request.setAttribute("couponMessage", couponCriteria);
			}else {
				if(isPersentageOffer) {
					providedOffer = overallAmount * (Integer.parseInt(offerAmount.substring(0, offerAmount.length()-1))/100.0);
					overallAmount -= providedOffer;
				}else {
					providedOffer = Integer.parseInt(offerAmount)+0.0;
					overallAmount = overallAmount - providedOffer;
				}
				request.setAttribute("providedOffer", providedOffer);
				request.setAttribute("couponMessage", "Coupon Applied! "+providedOffer+" RS offer...");
			}
			
		}else {
			request.setAttribute("providedOffer", 0);
			request.setAttribute("couponMessage", "Invalid Coupon!");
		}
		request.getRequestDispatcher("cart.jsp").forward(request, response);
		
	}


}
