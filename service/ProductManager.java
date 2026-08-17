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