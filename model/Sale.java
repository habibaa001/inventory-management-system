package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Sale implements Serializable{
    private static final long serialVersionUID=1L;
    private int saleId;
    private int productId;
    private int quantity;
    private double sellingPrice;
    private double totalAmount;
    private double profit;
    private LocalDate saleDate;

    public Sale(int saleId,int productId,int quantity,double sellingPrice,double purchasePrice,LocalDate saleDate){
        this.saleId=saleId;
        this.productId=productId;
        this.quantity=quantity;
        this.sellingPrice=sellingPrice;
        this.totalAmount=quantity*sellingPrice;
        this.profit=quantity*(sellingPrice-purchasePrice);
        this.saleDate=saleDate;
    }

    public int getSaleId(){return saleId;}
    public void setSaleId(int saleId){this.saleId=saleId;}
    public int getProductId(){return productId;}
    public void setProductId(int productId){this.productId=productId;}
    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity=quantity;}
    public double getSellingPrice(){return sellingPrice;}
    public void setSellingPrice(double sellingPrice){this.sellingPrice=sellingPrice;}
    public double getTotalAmount(){return totalAmount;}
    public double getProfit(){return profit;}
    public LocalDate getSaleDate(){return saleDate;}
    public void setSaleDate(LocalDate saleDate){this.saleDate=saleDate;}

    @Override
    public String toString(){
        return "Sale{"+"saleId="+saleId+", productId="+productId+", quantity="+quantity+", sellingPrice="+sellingPrice+", totalAmount="+totalAmount+", profit="+profit+", saleDate="+saleDate+'}';
    }
}