package service;

import model.Product;
import model.Purchase;
import util.FileManager;
import util.InputValidator;

import java.time.LocalDate;
import java.util.List;

public class PurchaseManager{
    private static final String FILE_PATH="data/purchases.dat";
    private List<Purchase> purchases;
    private final ProductManager productManager;
    public PurchaseManager(ProductManager productManager){
        this.productManager=productManager;
        purchases=FileManager.loadData(FILE_PATH);
    }
    public void purchaseStock(){
        int productId=InputValidator.getPositiveInt("Enter Product ID: ");
        Product product=productManager.findProductById(productId);

        if(product==null){
            System.out.println("Product not found.");
            return;
        }
        int quantity=InputValidator.getPositiveInt("Enter Quantity: ");
        double purchasePrice=InputValidator.getPositiveDouble("Enter Purchase Price: ");
        int purchaseId=getNextPurchaseId();
        Purchase purchase=new Purchase(purchaseId,productId,quantity,purchasePrice,LocalDate.now());
        product.setQuantity(product.getQuantity()+quantity);
        product.setPurchasePrice(purchasePrice);
        purchases.add(purchase);
        savePurchases();
        System.out.println("Stock purchased successfully.");
        System.out.println("Purchase ID: "+purchaseId);
        System.out.println("Total Cost: "+String.format("%.2f",purchase.getTotalCost()));
        System.out.println("Current Stock: "+product.getQuantity());
    }

    private int getNextPurchaseId(){
        int maxId=0;
        for(Purchase purchase:purchases){
            if(purchase.getPurchaseId()>maxId){
                maxId=purchase.getPurchaseId();
            }
        }
        return maxId+1;
    }
    private void savePurchases(){
        FileManager.saveData(FILE_PATH,purchases);
    }

    public List<Purchase> getPurchases(){
        return purchases;
    }
}