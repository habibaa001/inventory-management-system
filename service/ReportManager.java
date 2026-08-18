package service;

import model.Product;
import model.Sale;

import java.util.List;

public class ReportManager{
    private final ProductManager productManager;
    private final SaleManager saleManager;

    public ReportManager(ProductManager productManager,SaleManager saleManager){
        this.productManager=productManager;
        this.saleManager=saleManager;
    }

    public void stockAlert(){
        List<Product> products=productManager.getProducts();
        boolean found=false;

        System.out.println("\n===== Stock Alert =====");

        for(Product product:products){
            if(product.getQuantity()<=product.getMinimumStockLevel()){
                System.out.println("Product ID: "+product.getProductId());
                System.out.println("Product Name: "+product.getProductName());
                System.out.println("Current Stock: "+product.getQuantity());
                System.out.println("Minimum Stock: "+product.getMinimumStockLevel());
                System.out.println("----------------------------");
                found=true;
            }
        }

        if(!found){
            System.out.println("No low-stock products.");
        }
    }

    public void profitReport(){
        List<Sale> sales=saleManager.getSales();

        if(sales.isEmpty()){
            System.out.println("No sales records available.");
            return;
        }

        double totalSales=0;
        double totalProfit=0;

        for(Sale sale:sales){
            totalSales+=sale.getTotalAmount();
            totalProfit+=sale.getProfit();
        }

        System.out.println("\n===== Profit Report =====");
        System.out.printf("Total Sales Amount : %.2f%n",totalSales);
        System.out.printf("Total Profit       : %.2f%n",totalProfit);
        System.out.println("Total Transactions : "+sales.size());

        System.out.println("\n===== Sales Details =====");

        for(Sale sale:sales){
            System.out.printf(
                    "Sale ID: %d | Product ID: %d | Quantity: %d | Amount: %.2f | Profit: %.2f | Date: %s%n",
                    sale.getSaleId(),
                    sale.getProductId(),
                    sale.getQuantity(),
                    sale.getTotalAmount(),
                    sale.getProfit(),
                    sale.getSaleDate()
            );
        }
    }
}