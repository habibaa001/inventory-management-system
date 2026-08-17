package model;
import java.io.Serializable;
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private String productName;
    private String category;
    private double purchasePrice;
    private double sellingPrice;
    private int quantity;
    private int minimumStockLevel;
    public Product(int productId, String productName, String category, double purchasePrice, double sellingPrice,
            int quantity, int minimumStockLevel) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.minimumStockLevel = minimumStockLevel;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public double getSellingPrice() {
        return sellingPrice;
    }
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getMinimumStockLevel() {
        return minimumStockLevel;
    }
    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                ", minimumStockLevel=" + minimumStockLevel +
                '}';
    }
}