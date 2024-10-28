package com.billingsystem.Model;

public class Product {
	private int id;
    private String name;
    private String description;
    private double price;
    private int stockLeft;
    private int usualStock; 
    private double buyerPrice; 
    private double totalSold; 

    public Product(String name, String description, double price, int stockLeft, 
                   int usualStock, double buyerPrice, double totalSold) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockLeft = stockLeft;
        this.usualStock = usualStock;
        this.buyerPrice = buyerPrice;
        this.totalSold = totalSold;
    }
    
    public Product() {
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double sellingPrice) {
		this.price = sellingPrice;
	}

	public int getStockLeft() {
		return stockLeft;
	}

	public void setStockLeft(int stockLeft) {
		this.stockLeft = stockLeft;
	}

	public int getUsualStock() {
		return usualStock;
	}

	public void setUsualStock(int usualCount) {
		this.usualStock = usualCount;
	}


	public double getBuyerPrice() {
		return buyerPrice;
	}

	public void setBuyerPrice(double buyerPrice) {
		this.buyerPrice = buyerPrice;
	}

	public double getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(double profit) {
		this.totalSold = profit;
	}
    
}
