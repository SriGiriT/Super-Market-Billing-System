package com.billingsystem.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CouponService {
	public Map<String, List<String>> getCouponCodes(){
		Map<String, List<String>> hashMap = new HashMap<>();
		File file = new File("/home/girig/prog/java/servelet/SuperMarketBilling/src/main/resources/coupon.txt");
		try (Scanner sc = new Scanner(file)){
			while(sc.hasNextLine()) {
				List<String> eachLine = Arrays.asList(sc.nextLine().split("#"));
				hashMap.put(eachLine.get(0), eachLine.subList(1, eachLine.size()));
			}
			return hashMap;
		}catch(Exception e) {
			return hashMap;
		}
	}
}
