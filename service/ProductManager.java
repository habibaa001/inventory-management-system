package service;

import java.util.List;
import model.Product;
import util.FileManager;
import util.InputValidator;

public class ProductManager{
    private static final String FILE_PATH="data/products.dat";
    private List<Product> products;
    public ProductManager(){
        products=FileManager.loadData(FILE_PATH);
    }
    public void addProduct(){
        int id=InputValidator.getPositiveInt("Enter Product ID: ");
        if(findProductById(id)!=null){
            System.out.println("Product ID already exists.");
            return;
        }
        String name=InputValidator.getString("Enter Product Name: ");
        String category=InputValidator.getString("Enter Category: ");
        double purchasePrice=InputValidator.getPositiveDouble("Enter Purchase Price: ");
        double sellingPrice=InputValidator.getPositiveDouble("Enter Selling Price: ");
        int quantity=InputValidator.getInt("Enter Quantity: ");
        int minimumStock=InputValidator.getPositiveInt("Enter Minimum Stock Level: ");
        if(quantity<0){
            System.out.println("Quantity cannot be negative.");
            return;
        }
        Product product=new Product(id,name,category,purchasePrice,sellingPrice,quantity,minimumStock);
        products.add(product);
        saveProducts();
        System.out.println("Product added successfully.");
    }
    public void updateProduct(){
        int id=InputValidator.getPositiveInt("Enter Product ID to update: ");
        Product product=findProductById(id);
        if(product==null){
            System.out.println("Product not found.");
            return;
        }
        String name=InputValidator.getString("Enter Product Name: ");
        String category=InputValidator.getString("Enter Category: ");
        double purchasePrice=InputValidator.getPositiveDouble("Enter Purchase Price: ");
        double sellingPrice=InputValidator.getPositiveDouble("Enter Selling Price: ");
        int quantity=InputValidator.getInt("Enter Quantity: ");
        int minimumStock=InputValidator.getPositiveInt("Enter Minimum Stock Level: ");
        if(quantity<0){
            System.out.println("Quantity cannot be negative.");
            return;
        }
        product.setProductName(name);
        product.setCategory(category);
        product.setPurchasePrice(purchasePrice);
        product.setSellingPrice(sellingPrice);
        product.setQuantity(quantity);
        product.setMinimumStockLevel(minimumStock);
        saveProducts();
        System.out.println("Product updated successfully.");
    }
    public void deleteProduct(){
        int id=InputValidator.getPositiveInt("Enter Product ID to delete: ");
        Product product=findProductById(id);
        if(product==null){
            System.out.println("Product not found.");
            return;
        }
        products.remove(product);
        saveProducts();
        System.out.println("Product deleted successfully.");
    }
    public void viewProducts(){
        if(products.isEmpty()){
            System.out.println("No products available.");
            return;
        }
        System.out.println("\n===== Product List =====");
        System.out.printf("%-8s %-20s %-15s %-15s %-15s %-10s %-12s%n",
                "ID","Name","Category","Purchase Price","Selling Price","Quantity","Min Stock");
        for(Product product:products){
            System.out.printf("%-8d %-20s %-15s %-15.2f %-15.2f %-10d %-12d%n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getCategory(),
                    product.getPurchasePrice(),
                    product.getSellingPrice(),
                    product.getQuantity(),
                    product.getMinimumStockLevel());
        }
    }
    public Product findProductById(int id){
        for(Product product:products){
            if(product.getProductId()==id)return product;
        }
        return null;
    }
    public List<Product> getProducts(){
        return products;
    }
    private void saveProducts(){
        FileManager.saveData(FILE_PATH,products);
    }
}