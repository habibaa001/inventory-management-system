package service;

import model.Product;
import model.Sale;
import util.FileManager;
import util.InputValidator;

import java.time.LocalDate;
import java.util.List;

public class SaleManager{
    private static final String FILE_PATH="data/sales.dat";
    private List<Sale> sales;
    private final ProductManager productManager;

    public SaleManager(ProductManager productManager){
        this.productManager=productManager;
        sales=FileManager.loadData(FILE_PATH);
    }

    public void sellProduct(){
        int productId=InputValidator.getPositiveInt("Enter Product ID: ");
        Product product=productManager.findProductById(productId);

        if(product==null){
            System.out.println("Product not found.");
            return;
        }

        int quantity=InputValidator.getPositiveInt("Enter Quantity: ");

        if(quantity>product.getQuantity()){
            System.out.println("Insufficient stock.");
            System.out.println("Available Stock: "+product.getQuantity());
            return;
        }

        double sellingPrice=product.getSellingPrice();
        int saleId=getNextSaleId();

        Sale sale=new Sale(
                saleId,
                productId,
                quantity,
                sellingPrice,
                product.getPurchasePrice(),
                LocalDate.now()
        );

        product.setQuantity(product.getQuantity()-quantity);
        sales.add(sale);

        saveSales();

        System.out.println("Product sold successfully.");
        System.out.println("Sale ID: "+saleId);
        System.out.println("Total Amount: "+String.format("%.2f",sale.getTotalAmount()));
        System.out.println("Profit: "+String.format("%.2f",sale.getProfit()));
        System.out.println("Remaining Stock: "+product.getQuantity());
    }

    private int getNextSaleId(){
        int maxId=0;

        for(Sale sale:sales){
            if(sale.getSaleId()>maxId){
                maxId=sale.getSaleId();
            }
        }

        return maxId+1;
    }

    private void saveSales(){
        FileManager.saveData(FILE_PATH,sales);
    }

    public List<Sale> getSales(){
        return sales;
    }
}