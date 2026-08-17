package model;
import java.io.Serializable;
import java.time.LocalDate;
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;
    private int purchaseId;
    private int productId;
    private int quantity;
    private double purchasePrice;
    private double totalCost;
    private LocalDate purchaseDate;
    public Purchase(int purchaseId, int productId, int quantity,
                    double purchasePrice, LocalDate purchaseDate) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.totalCost = quantity * purchasePrice;
        this.purchaseDate = purchaseDate;
    }
    public int getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalCost = this.quantity * this.purchasePrice;
    }
    public double getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
        this.totalCost = this.quantity * this.purchasePrice;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseId=" + purchaseId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", purchasePrice=" + purchasePrice +
                ", totalCost=" + totalCost +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}