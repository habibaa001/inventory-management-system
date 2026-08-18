import service.ProductManager;
import service.PurchaseManager;
import service.ReportManager;
import service.SaleManager;
import util.InputValidator;

public class Main{
    public static void main(String[] args){
        ProductManager productManager=new ProductManager();
        PurchaseManager purchaseManager=new PurchaseManager(productManager);
        SaleManager saleManager=new SaleManager(productManager);
        ReportManager reportManager=new ReportManager(productManager,saleManager);

        while(true){
            showMenu();
            int choice=InputValidator.getInt("Enter your choice: ");

            switch(choice){
                case 1:
                    productManager.addProduct();
                    break;
                case 2:
                    productManager.updateProduct();
                    break;
                case 3:
                    productManager.deleteProduct();
                    break;
                case 4:
                    productManager.viewProducts();
                    break;
                case 5:
                    purchaseManager.purchaseStock();
                    break;
                case 6:
                    saleManager.sellProduct();
                    break;
                case 7:
                    reportManager.stockAlert();
                    break;
                case 8:
                    reportManager.profitReport();
                    break;
                case 9:
                    System.out.println("Thank you for using Inventory Management System.");
                    return;
                default:
                    System.out.println("Invalid choice! Please select 1-9.");
            }

            System.out.println();
        }
    }

    private static void showMenu(){
        System.out.println("===== Inventory Management System =====");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. View Products");
        System.out.println("5. Purchase Stock");
        System.out.println("6. Sell Product");
        System.out.println("7. Stock Alert");
        System.out.println("8. Profit Report");
        System.out.println("9. Exit");
    }
}